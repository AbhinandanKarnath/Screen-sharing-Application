package com.project.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import com.project.model.User;
import com.project.view.Sounds;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SceneController
{
    private Stage stage;
    private Scene scene;
    @FXML TextField userName;
    @FXML TextField designation;
    @FXML Button ToHome;
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
            System.out.println(e.getMessage());
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
            scene = new Scene(root);
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
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void openNetworkSettingWindow()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/networkSetting-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}