package com.project.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.project.main.BroadcastSender.closeSocket;

public class SceneController
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void broadcasterScene(ActionEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sender-view.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
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
            Parent root = FXMLLoader.load(getClass().getResource("Receiver-view.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}