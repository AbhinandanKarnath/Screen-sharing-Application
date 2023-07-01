package com.project.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sounds {

    private String soundPath;
    private static File file ;
    private static Media media;
    private static MediaPlayer player;

    private static void playSound(String filepath)
    {
        file = new File(filepath);
        media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);

        player.play();
    }
    public void loginSound()
    {
//            soundPath = "E:\\intelijCVVProject\\Main\\src\\main\\resources\\com\\project\\controller\\Sounds\\jarvis.mp3";
            soundPath = "E:\\intelijCVVProject\\Main\\src\\main\\resources\\com\\project\\controller\\Sounds\\home.mp3a" ;//
            playSound(soundPath);
    }
    public void setNetwork()
    {
        soundPath= "E:\\intelijCVVProject\\Main\\src\\main\\resources\\com\\project\\controller\\Sounds\\sendSound.mp3";
        playSound(soundPath);
    }
    public void rMusic()
    {
        soundPath= "E:\\intelijCVVProject\\Main\\src\\main\\resources\\com\\project\\controller\\Sounds\\guitarR.mp3";
        playSound(soundPath);
    }
    public void r1Music()
    {
        soundPath = "E:\\intelijCVVProject\\Main\\src\\main\\resources\\com\\project\\controller\\Sounds\\guitarR1.mp3";
        playSound(soundPath);
    }
}
