package com.project.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.project.main.BroadcastSender.send;

public class ApplicationController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imageField;

    @FXML
    protected void ReadyToReceive() {

        BroadcastReceiver obj = new BroadcastReceiver();
        Image image = obj.receiver();
        imageField.setImage(image);

    }
    @FXML
    protected void ReadyToSend()
    {
        send();
    }
}