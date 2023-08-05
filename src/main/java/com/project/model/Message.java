package com.project.model;

import java.net.*;
import java.nio.charset.StandardCharsets;

import static com.project.model.User.getUserObject;

public class Message {
    private static boolean set = false;
    private static MulticastSocket socket;
    private static InetAddress inetAddress;
    static User user ;
    private static String userName;
    private static String userDesignation;
    private static int port;
    private static String inetAddressName;
    private static String networkInterfaceName;

    private static void setNetworkAddress() {
        NetworkSettings settings = new NetworkSettings();
        port = settings.getChatPortNumber();
        inetAddressName = settings.getChatNetworkInetAddress();
        networkInterfaceName = settings.getChatNetworkInterfaceName();
    }
    private static void setUserInfo()
    {
        userName = user.getUserName();
        userDesignation = user.getDesignation();
    }
    public static void setMessageNetwork()
    {
        try
        {
            setNetworkAddress();
            socket = new MulticastSocket(port);
            socket.setTimeToLive(1);
            inetAddress = InetAddress.getByName(inetAddressName);
            socket.joinGroup(new InetSocketAddress(inetAddress, port) , NetworkInterface.getByName(networkInterfaceName));

            System.out.println("chat network screen property :"+port+" "+inetAddressName+" "+networkInterfaceName );
            set = true;
            user = getUserObject();
            setUserInfo();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sendMessageToEveryone(String message)
    {
        try
        {
            if(set)
            {
                message = userName+":"+message;
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(messageBytes , messageBytes.length , inetAddress , port);

                socket.send(packet);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return "'"+userName+"' is out of the chat for now....";
    }
    public static void closeChatConnection() {
        set = false;
        if(!socket.isClosed())
        {
            socket.close();
        }
    }
}