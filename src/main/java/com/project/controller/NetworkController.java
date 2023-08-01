package com.project.controller;

import com.project.model.NetworkSettings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NetworkController extends AnchorPane {

    @FXML TextField screenPort;                                                                                     // Default network settings
    @FXML TextField chatPort;
    @FXML TextField screenInetAddress;
    @FXML TextField chatInetAddress;
    @FXML TextField screenInterfaceName;
    @FXML TextField chatInterfaceName;
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

    @FXML
    public void saveChanges()
    {

    }
}