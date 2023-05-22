package com.project.main;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BroadcastSender {
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

            Image image1 = new Image(bi);

            InetAddress group = InetAddress.getByName("224.0.0.0");
            MulticastSocket socket = new MulticastSocket(5000);

            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("localhost"));

            System.out.println(bytes.length);
            int size = 1024;
            int i = 0 ;
            Thread.sleep(5000);
            while(i<bytes.length)
            {

                int length = Math.min(size, bytes.length - i );
                byte[] smallPacket = new byte[length];
                System.arraycopy(bytes, i, smallPacket, 0, length);
                // System.out.println(Arrays.toString(smallPacket));
                DatagramPacket packet = new DatagramPacket(smallPacket, smallPacket.length, group, 5000);
                // DatagramPacket packet2 = new
                socket.send(packet);
                i += length;
            }
            socket.close();

        }
        catch (Exception e)
        {
            System.out.println(e);

        }
    }
}