package com.project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/signInBackground-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("one-for-all");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("images/1.png")));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    public static void main(String[] args)
    {
            launch();
    }
}