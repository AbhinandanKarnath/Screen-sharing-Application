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
    private static Rectangle rectangle;
    static int count=0;
    static int size = 1024;
    static int i = 0 ;
    static int length ;
    public static void SetNetwork()
    {
        try
        {

            NetworkSettings settings = new NetworkSettings();
            int port = settings.getScreenPortNumber();
            String inetAddressName = settings.getScreenNetworkInetAddress();
            String networkInterfaceName = settings.getScreenNetworkInterfaceName();

            group = InetAddress.getByName(inetAddressName);

            socket = new MulticastSocket(port);
            socket.joinGroup(new InetSocketAddress(group , port) , NetworkInterface.getByName(networkInterfaceName));
            socket.setTimeToLive(1);
            robot = new Robot();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            rectangle = new Rectangle( 0 , 0 , (int) dimension.getWidth() , (int) dimension.getHeight());
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
                BufferedImage image = robot.createScreenCapture(rectangle);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg" , bos);
                bos.flush();
                byte[] bytes = bos.toByteArray();

                i = 0 ;
                while(i<bytes.length )
                {
                    try
                    {

                        length = Math.min(size, bytes.length - i );
                        byte[] smallPacket = new byte[length];
                        System.arraycopy(bytes, i, smallPacket, 0, length);
                        DatagramPacket packet = new DatagramPacket(smallPacket, smallPacket.length, group, 5000);
                        socket.send(packet);
                        i += length;
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
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