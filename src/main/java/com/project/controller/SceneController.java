package com.project.controller;

import com.project.model.User;
import com.project.view.Sounds;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneController
{
    private Stage stage;
    private Scene scene;
    @FXML TextField userName;
    @FXML TextField designation;
    @FXML Button ToHome;
    @FXML Label homeUserName;
    Sounds sounds = new Sounds();
    User user;
    static String name ;
    static String signInAs;

    @FXML
    public void signIn()
    {
        name = userName.getText();
        signInAs = designation.getText();
        user = new User();
        user.setUserObject(user);
        user.setUserName(name);
        user.setDesignation(signInAs);
        ToHome.setDisable(false);
    }

    @FXML
    public void toHomePage(ActionEvent event)
    {
        try
        {
            sounds.loginSound();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/homeBackground-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            grp controller = fxmlLoader.getController();
            controller.setTheLabel(name);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    public void broadcasterScene(ActionEvent event)
    {
        try {
            sounds.r1Music();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/sender-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            ChatBoxController message = fxmlLoader.getController();
            scene = new Scene(root);
//            scene.setOnKeyPressed(keyEvent -> {
//                if(keyEvent.getCode().equals(KeyCode.ENTER))
//                {
//                    message.sendMessage();
//                }
//            });
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void broadcastReceiverScene(ActionEvent event)
    {
        try {

            sounds.rMusic();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Receiver-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            ChatBoxController message = fxmlLoader.getController();
            scene = new Scene(root);
//            scene.setOnKeyPressed(keyEvent -> {
//                if(keyEvent.getCode().equals(KeyCode.ENTER))
//                {
//                    message.sendMessage();
//                }
//            });
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}