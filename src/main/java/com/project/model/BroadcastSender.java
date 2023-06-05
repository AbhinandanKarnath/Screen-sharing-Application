package com.project.model;

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

    public static void SetNetwork()
    {
        try
        {
            group = InetAddress.getByName("225.0.0.0");

            socket = new MulticastSocket(5000);
            System.out.println("1");
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("summit"));
            System.out.println("network established");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("2");
        }
    }
    public static void send()
    {
        try
        {
            Robot robot = new Robot();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            Rectangle rectangle = new Rectangle( 0 , 0 , (int)dimension.getWidth() , (int)dimension.getHeight());

            BufferedImage image = robot.createScreenCapture(rectangle);

            ByteArrayOutputStream bos =new ByteArrayOutputStream();
            ImageIO.write(image, "jpg" , bos);
            bos.flush();
            byte[] bytes = bos.toByteArray();

            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);

            int size = 1024;
            int i = 0 ;
            while(i<bytes.length)
            {

                int length = Math.min(size, bytes.length - i );
                byte[] smallPacket = new byte[length];
                System.arraycopy(bytes, i, smallPacket, 0, length);
                DatagramPacket packet = new DatagramPacket(smallPacket, smallPacket.length, group, 5000);
                socket.send(packet);
                i += length;
            }
        }
        catch (SocketException socketException)
        {
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println(e);

        }
    }
    public static void closeSocket()
    {
        socket.close();
    }
}