package com.project.main;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static com.project.main.BroadcastSender.*;

public class ApplicationController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imageField;
    @FXML
    private CheckBox sendCheckbox;
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
                        imageField.setImage(obj.receiver());
                    }
                }
            };
            new Thread(runnable).start();
        }
    }
    @FXML
    protected void ReadyToSend()
    {
        SetNetwork();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (sendCheckbox.isSelected())
                {
                    send();
                }
            }
        };
        new Thread(runnable).start();
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
}