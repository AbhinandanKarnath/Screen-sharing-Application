package com.project.main;

import com.project.controller.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class SceneController
{
    private Stage stage;
    private Scene scene;

    @FXML
    public void signInAsUser(ActionEvent event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void broadcasterScene(ActionEvent event)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sender-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            ApplicationController message = fxmlLoader.getController();
            scene = new Scene(root);
            scene.setOnKeyPressed(keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.ENTER))
                {
                    message.sendMessage();
                }
            });
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    @FXML
    public void broadcastReceiverScene(ActionEvent event)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Receiver-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            ApplicationController message = fxmlLoader.getController();
            scene = new Scene(root);
            scene.setOnKeyPressed(keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.ENTER))
                {
                    message.sendMessage();
                }
            });
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}