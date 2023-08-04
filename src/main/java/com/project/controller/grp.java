package com.project.controller;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class grp {
    @FXML
    Label homeUserName;
    @FXML Circle circle1;
    @FXML Circle circle2;
    @FXML Circle circle3;
    @FXML Circle circle4;
    @FXML Circle circle5;
    @FXML Circle circle6;
    @FXML Circle circle7;
    @FXML Circle circle8;
    @FXML Circle circle9;
    @FXML Circle circle10;

    public void setTheLabel(String name)
    {
        homeUserName.setText("Namaskara :"+name);
    }
    @FXML
    public void mouseOnPane1()
    {
        TranslateTransition translateTransition1 = new TranslateTransition();
        TranslateTransition translateTransition2 = new TranslateTransition();
        double bound1 = circle1.getLayoutX();

        translateTransition1.setNode(circle1);
        translateTransition1.setDuration(Duration.millis(2000));
        translateTransition1.setCycleCount(3);
        translateTransition1.setByY(-50);
        translateTransition1.setAutoReverse(true);

        translateTransition2.setNode(circle2);
        translateTransition2.setDuration(Duration.millis(1000));
        translateTransition2.setCycleCount(4);
        translateTransition2.setByY(50);
        translateTransition2.setAutoReverse(true);

        translateTransition1.play();
        translateTransition2.play();
    }
    @FXML
    public void mouseOnPane2()
    {
        TranslateTransition translateTransition1 = new TranslateTransition();
        TranslateTransition translateTransition2 = new TranslateTransition();

        translateTransition1.setNode(circle3);
        translateTransition1.setDuration(Duration.millis(3000));
        translateTransition1.setCycleCount(5);
        translateTransition1.setByY(-25);
        translateTransition1.setAutoReverse(true);

        translateTransition2.setNode(circle4);
        translateTransition2.setDuration(Duration.millis(4500));
        translateTransition2.setCycleCount(6);
        translateTransition2.setByY(50);
        translateTransition2.setAutoReverse(true);

        translateTransition1.play();
        translateTransition2.play();
    }
    @FXML
    public void mouseOnPane3()
    {
        TranslateTransition translateTransition1 = new TranslateTransition();
        TranslateTransition translateTransition2 = new TranslateTransition();
        TranslateTransition translateTransition3 = new TranslateTransition();

        translateTransition1.setNode(circle5);
        translateTransition1.setDuration(Duration.millis(2000));
        translateTransition1.setCycleCount(3);
        translateTransition1.setByY(-5);
        translateTransition1.setAutoReverse(true);

        translateTransition2.setNode(circle6);
        translateTransition2.setDuration(Duration.millis(1000));
        translateTransition2.setCycleCount(4);
        translateTransition2.setByY(1);
        translateTransition2.setAutoReverse(true);

        translateTransition3.setNode(circle7);
        translateTransition3.setDuration(Duration.millis(1000));
        translateTransition3.setCycleCount(4);
        translateTransition3.setByY(25);
        translateTransition3.setAutoReverse(true);

        translateTransition1.play();
        translateTransition2.play();
        translateTransition3.play();
    }
    @FXML
    public void mouseOnPane4()
    {
        TranslateTransition translateTransition1 = new TranslateTransition();
        TranslateTransition translateTransition2 = new TranslateTransition();

        translateTransition1.setNode(circle8);
        translateTransition1.setDuration(Duration.millis(2000));
        translateTransition1.setCycleCount(3);
        translateTransition1.setByY(-50);
        translateTransition1.setAutoReverse(true);

        translateTransition2.setNode(circle9);
        translateTransition2.setDuration(Duration.millis(1000));
        translateTransition2.setCycleCount(4);
        translateTransition2.setByY(50);
        translateTransition2.setAutoReverse(true);

        translateTransition1.play();
        translateTransition2.play();
    }
    @FXML
    public void ChangeColor()
    {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(circle10);
        rotateTransition.setDuration(Duration.millis(2000));
        rotateTransition.setCycleCount(3);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

    }

    @FXML
    public void rotateColor()
    {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(circle1);
        rotateTransition.setDuration(Duration.millis(2000));
        rotateTransition.setCycleCount(3);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }
}