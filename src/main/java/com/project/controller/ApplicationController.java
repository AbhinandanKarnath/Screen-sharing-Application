package com.project.controller;

import com.project.view.Sounds;
import javafx.event.ActionEvent;
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
    @FXML private ImageView imageField;
    @FXML private CheckBox sendCheckbox;
    @FXML private CheckBox receiveCheckbox;
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
                    System.out.println(e.getMessage());
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
                    try
                    {
                        send();
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                };
                new Thread(runnable).start();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    @FXML
    public void closeConnection(ActionEvent event)
    {
        try
        {
            send = false;
            sendCheckbox.setSelected(send);
            closeSocket();
            sendCheckbox.setText("GO ONLINE");
            new ChatBoxController().End();
            new SceneController().toHomePage(event);
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
            System.out.println(e.getMessage());
        }
    }
}