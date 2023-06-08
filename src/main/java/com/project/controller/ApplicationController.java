package com.project.controller;

import com.project.model.BroadcastReceiver;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

import static com.project.model.BroadcastSender.*;
import static com.project.model.Message.*;

public class ApplicationController{
    @FXML private VBox vBox;
    @FXML private ImageView imageField;
    @FXML private CheckBox sendCheckbox;
    @FXML private TextField message;
    @FXML private CheckBox receiveCheckbox;
    @FXML private CheckBox ChatCheckbox;

    Task<Void> receiveMessagesTask ;
    BroadcastReceiver obj;
    public static boolean send;

    @FXML
    protected void ReadyToReceive() {

        obj = new BroadcastReceiver();

        if(obj.joinNetwork())
        {
            Runnable runnable = () -> {
                try
                {
                    while (receiveCheckbox.isSelected())
                    {
                        imageField.setImage(new Image(new ByteArrayInputStream(obj.receiver())));
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            };
            new Thread(runnable).start();
        }
    }
    @FXML
    protected void ReadyToSend() {
        send = sendCheckbox.isSelected();
        if(send)
        {
            try {
                SetNetwork();
                Runnable runnable = () -> {
//                    while (send)
//                    {
                        send();
//                    }
                };
                new Thread(runnable).start();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    @FXML
    public void closeConnection()
    {
        send = false;
        sendCheckbox.setSelected(false);
        closeSocket();
        sendCheckbox.setText("GO ONLINE");
    }
    @FXML
    public void exitGroup()
    {
        obj.endConnectedNetwork();
    }
    @FXML
    public void sendMessage()
    {
        sendMessageToEveryone(message.getText());
    }
    @FXML
    public void updateChat() {
        if(ChatCheckbox.isSelected()) {
            setMessageNetwork();
            System.out.println("The message network setted.......");
            try {
                receiveMessagesTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        while (!isCancelled()) {
                            String message = receiveMessages();

                            Platform.runLater(() -> {
                                Label label = new Label(message);
//                                label.setBackground(Color.web("#0076a3")); cannot chang the chat label color
                                label.setFont(new Font("Arial",16));
                                vBox.getChildren().add(label);
                            });
                        }
                        return null;
                    }
                };
                new Thread(receiveMessagesTask).start();
            } catch (Exception e) {
                System.out.println("here.........................");
                System.out.println(e);
            }
        } else {
            try {
                receiveMessagesTask.cancel();
                closeChatConnection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}