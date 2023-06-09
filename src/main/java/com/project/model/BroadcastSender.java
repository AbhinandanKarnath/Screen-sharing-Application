package com.project.model;

import com.project.controller.ApplicationController;

import java.io.ByteArrayOutputStream;
import java.net.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BroadcastSender {
    private static InetAddress group;
    private static MulticastSocket socket;
    private static Robot robot;
    private static Toolkit toolkit;
    private static Dimension dimension;
    private static Rectangle rectangle;
    private static DatagramPacket packet;
    private static byte[] smallPacket;
    private static BufferedImage image;
    private static ByteArrayOutputStream bos;
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
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("summit"));
            robot = new Robot();
            toolkit = Toolkit.getDefaultToolkit();
            dimension = toolkit.getScreenSize();
            rectangle = new Rectangle( 0 , 0 , (int)dimension.getWidth() , (int)dimension.getHeight());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void send()
    {
        while(ApplicationController.send){
            try
            {
                image = robot.createScreenCapture(rectangle);
                bos =new ByteArrayOutputStream();
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
                e.printStackTrace();
            }
        }
    }
    public static void closeSocket()
    {
        if(!socket.isClosed())
        {
            socket.close();
        }
        count = 0;
    }
}