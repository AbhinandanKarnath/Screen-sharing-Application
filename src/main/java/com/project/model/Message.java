package com.project.model;

import java.net.*;

import static com.project.model.User.getUserObject;

public class Message {
    private static boolean set = false;
    private static int port=1234;
    private static MulticastSocket socket;
    private static InetAddress inetAddress;
    static User user ;
    private static String userName;
    private static String userDesignation;
    private static void setUserInfo()
    {
        userName = user.getUserName();
        userDesignation = user.getDesignation();
    }
    public static void setMessageNetwork()
    {
        try
        {
            socket = new MulticastSocket(port);
            socket.setTimeToLive(1);
            inetAddress = InetAddress.getByName("226.0.0.0");
            socket.joinGroup(new InetSocketAddress(inetAddress, port) , NetworkInterface.getByName("summitMessage"));
            set = true;
            user = getUserObject();
            setUserInfo();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void sendMessageToEveryone(String message)
    {
        try
        {
            if(set)
            {
                message = userName+":"+message;
                byte[] messageBytes = message.getBytes("UTF-8");
                DatagramPacket packet = new DatagramPacket(messageBytes , messageBytes.length , inetAddress , port);
                socket.send(packet);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public static String receiveMessages()
    {
        try
        {
            if(set)
            {
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data , data.length , inetAddress , port);
                socket.receive(packet);
                String message = new String(packet.getData() , 0 , packet.getLength());
                System.out.println(message);
                return message;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return "'"+userName+"' is out of the chat for now....";
    }
    public static void closeChatConnection() {
        set = false;
        socket.close();
    }
}