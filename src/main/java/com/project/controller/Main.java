package com.project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signInBackground-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("one-for-all");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public static void main(String[] args)
    {
            launch();
    }
}