package com.project.model;

import java.net.*;

public class Message {
    static int port=1234;
    static MulticastSocket socket;
    static InetAddress inetAddress;

    public static void setMessageNetwork()
    {
        try
        {
            socket = new MulticastSocket(port);
            socket.setTimeToLive(1);
            inetAddress = InetAddress.getByName("226.0.0.0");
            socket.joinGroup(new InetSocketAddress(inetAddress, port) , NetworkInterface.getByName("summitMessage"));
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
            byte[] messageBytes = message.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(messageBytes , messageBytes.length , inetAddress , port);
            socket.send(packet);
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
            System.out.println("Inside the receive Message");
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data , data.length , inetAddress , port);
            socket.receive(packet);
            String message = new String(packet.getData() , 0 , packet.getLength());
            System.out.println(message);
            return message;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    public static void closeChatConnection()
    {
        socket.close();
    }
}
