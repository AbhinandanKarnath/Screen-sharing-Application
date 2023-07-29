package com.project.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

import static com.project.model.BroadcastReceiver.*;
import static com.project.model.BroadcastSender.*;

import com.project.view.Sounds;

public class ApplicationController {
    @FXML private ImageView imageField;
    @FXML private CheckBox sendCheckbox;
    @FXML private CheckBox receiveCheckbox;
    public static boolean send;
    Sounds sounds = new Sounds();

    @FXML
    protected void ReadyToSend() {                         // Methode is called when the user presses Send button
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
                new Thread(runnable).start();           //new separate Thread is created to send the image.
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
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            new SceneController().toHomePage(event);
        }
    }

    @FXML
    protected void ReadyToReceive() {               // Methode is called when the user presses Receive button

        if(receiveCheckbox.isSelected())
        {
            if(joinNetwork())
            {
                sounds.setNetwork();
                Runnable runnable = () -> {
                    try
                    {
                        while (receiveCheckbox.isSelected())
                        {
                            imageField.setImage(new Image(new ByteArrayInputStream(receiver())));
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                };
                new Thread(runnable).start();           // new separate thread is created to receive the Image.
            }
        }
    }

    @FXML
    public void exitGroup(ActionEvent event)
    {
        try {
            endConnectedNetwork();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        finally {
            new SceneController().toHomePage(event);
        }
    }
}