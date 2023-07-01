package com.project.controller;

import com.project.model.User;
import com.project.view.Sounds;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;

public class SceneController
{
    private Stage stage;
    private Scene scene;
    @FXML TextField userName;
    @FXML TextField designation;
    @FXML Label homeUserName;
    @FXML CubicCurve wave;
    Sounds sounds = new Sounds();
    String name ;
    String signInAs;

    @FXML
    public void signInAsUser(ActionEvent event)
    {
        name = userName.getText();
        signInAs = designation.getText();
        sounds.loginSound();
        try
        {
            User user = new User();
            user.setUserObject(user);
            user.setUserName(name);
            user.setDesignation(signInAs);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            SceneController controller = fxmlLoader.getController();
            controller.setData(name);
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
            sounds.r1Music();
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

            sounds.rMusic();
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
    private void setData(String data)
    {
        homeUserName.setText("Hello "+data);
    }
    @FXML
    public void createWave()
    {
//        double y1 = wave.getControlY1();
//        double y2 = wave.getControlY2();
//
//        double setY1 = (y1 < -30 && y1 > -100) ? y1+1 : y1-1 ;
//        double setY2 = (y2 < -30 && y2 > -100) ? y2+1 : y2-1 ;
//        wave.setControlY1(setY1);
//        wave.setControlY2(setY2);
    }
    @FXML
    public void menuBar()
    {

    }
    private void playMusic() {

    }
}