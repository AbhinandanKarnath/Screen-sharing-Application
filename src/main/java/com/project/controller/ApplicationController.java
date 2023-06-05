package com.project.controller;

import com.project.model.BroadcastReceiver;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

import static com.project.model.BroadcastSender.*;
import static com.project.model.Message.*;

public class ApplicationController {
    @FXML private VBox vBox;
    @FXML private ImageView imageField;
    @FXML private CheckBox sendCheckbox;
    @FXML private TextField message;
    @FXML private CheckBox receiveCheckbox;
    BroadcastReceiver obj;
    @FXML
    protected void ReadyToReceive() {

        obj = new BroadcastReceiver();

        if(obj.joinNetwork())
        {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    while (receiveCheckbox.isSelected())
                    {
                        imageField.setImage(new Image(new ByteArrayInputStream(obj.receiver())));
                    }
                }
            };
            new Thread(runnable).start();
        }
        setMessageNetwork();
        System.out.println("messageNetwork set");
    }
    @FXML
    protected void ReadyToSend() {

        try {
            SetNetwork();
            Runnable runnable = () -> {
                while (sendCheckbox.isSelected())
                {
                    send();
                }
            };
            new Thread(runnable).start();
            setMessageNetwork();
            System.out.println("ready to receive message ");
            vBox.getChildren().add(new Label(receiveMessages()));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    public void closeConnection()
    {
        closeSocket();
        sendCheckbox.setSelected(false);
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
}