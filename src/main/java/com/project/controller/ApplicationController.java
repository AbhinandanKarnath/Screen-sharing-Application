package com.project.controller;

import com.project.view.Sounds;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

import com.project.model.BroadcastReceiver;

import static com.project.model.BroadcastSender.*;
import static com.project.model.Message.*;

public class ApplicationController {
    @FXML private VBox vBox;
    @FXML private ImageView imageField;
    @FXML private CheckBox sendCheckbox;
    @FXML private TextField message;
    @FXML private CheckBox receiveCheckbox;
    @FXML private CheckBox ChatCheckbox;

    private Task<Void> receiveMessagesTask ;
    private BroadcastReceiver obj;
    public static boolean send;
    Sounds sounds = new Sounds();

    @FXML
    protected void ReadyToReceive() {

        obj = new BroadcastReceiver();

        if(obj.joinNetwork())
        {
            sounds.setNetwork();
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
                sounds.setNetwork();
                Runnable runnable = () -> {
                        send();
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
        try
        {
            send = false;
            sendCheckbox.setSelected(send);
            ChatCheckbox.setSelected(send);
            closeSocket();
            sendCheckbox.setText("GO ONLINE");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void exitGroup()
    {
        try {
            obj.endConnectedNetwork();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());;
        }
    }
    @FXML
    public void sendMessage()
    {
        sendMessageToEveryone(message.getText());
        message.setText("");
    }
    @FXML
    public void updateChat()
    {
        try
        {
            if(ChatCheckbox.isSelected()) {
                setMessageNetwork();

                receiveMessagesTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        while (!isCancelled()) {
                            String message = receiveMessages();

                            Platform.runLater(() -> {
                                Label label = new Label(message);
                                label.setFont(new Font("Arial",16));
                                vBox.getChildren().add(label);
                            });
                        }
                        return null;
                    }
                };
                new Thread(receiveMessagesTask).start();
                sendMessageToEveryone(" is in the Chat");
            } else {
                receiveMessagesTask.cancel();
                closeChatConnection();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}