package com.project.model;

import com.project.controller.ApplicationController;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BroadcastSender {
    static InetAddress group;
    static MulticastSocket socket;

    static Robot robot;
    static Toolkit toolkit;
    static Dimension dimension;
    static Rectangle rectangle;
    static DatagramPacket packet;
    static byte[] smallPacket;
    static int count=0;
    static int size = 1024;
    static int i = 0 ;
    static int length ;
    public static void SetNetwork()
    {
        try
        {
            group = InetAddress.getByName("225.0.0.0");

            socket = new MulticastSocket(5000);
            System.out.println("1");
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("summit"));
            System.out.println("network established");
            robot = new Robot();
            toolkit = Toolkit.getDefaultToolkit();
            dimension = toolkit.getScreenSize();
            rectangle = new Rectangle( 0 , 0 , (int)dimension.getWidth() , (int)dimension.getHeight());
            System.out.println("The screen capture tools are ready too...");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("2");
        }
    }
    public static void send()
    {
        while(ApplicationController.send){
            try
            {
                BufferedImage image = robot.createScreenCapture(rectangle);
                ByteArrayOutputStream bos =new ByteArrayOutputStream();
                ImageIO.write(image, "jpg" , bos);
                bos.flush();
                byte[] bytes = bos.toByteArray();

                i = 0 ;
                while(i<bytes.length)
                {
                    length = Math.min(size, bytes.length - i );
                    smallPacket = new byte[length];
                    System.arraycopy(bytes, i, smallPacket, 0, length);
                    packet = new DatagramPacket(smallPacket, smallPacket.length, group, 5000);
                    socket.send(packet);
                    i += length;
                }
                System.out.println(count++);
            }
            catch (Exception e)
            {
                System.out.println(e);

            }
        }

    }
    public static void closeSocket()
    {
        socket.close();
        count = 0;
    }
}