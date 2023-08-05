package com.project.controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

import static com.project.model.Message.*;

public class ChatBoxController
{
    @FXML private VBox vBox;
    @FXML private TextField message;
    @FXML private CheckBox ChatCheckbox;
    private Task<Void> receiveMessagesTask ;

    public void End()
    {
        closeChatConnection();
    }
    @FXML
    public void sendMessage()
    {
        sendMessageToEveryone(message.getText());
        message.setText("");
    }
    @FXML
    public void updateChat()
    {
        try
        {
            if(ChatCheckbox.isSelected()) {
                setMessageNetwork();

                receiveMessagesTask = new Task<>() {
                    @Override
                    protected Void call(){
                        while (!isCancelled()) {
                            String message = receiveMessages();

                            Platform.runLater(() -> {
                                Label label = new Label(message);
                                label.setFont(new Font("Arial",15));

                                label.setStyle("-fx-background-color: rgb(20,20,20); -fx-background-radius: 15px; -fx-text-fill: #ffffff; -fx-padding: 5px");
                                label.setWrapText(true);
                                vBox.getChildren().add(label);
                            });
                        }
                        return null;
                    }
                };
                new Thread(receiveMessagesTask).start();
                sendMessageToEveryone(" is in the Chat");
            } else {
                receiveMessagesTask.cancel();

                sendMessageToEveryone(" logged off");
                closeChatConnection();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}