package com.project.model;

import java.net.*;

public class BroadcastReceiver {
    private static MulticastSocket socket;
    private static InetAddress group;
    private static DatagramPacket packet;
    private static byte[] complete;
    private static int port;
    private static String inetAddressName;
    private static String networkInterfaceName;
    private static int count = 0 ;
    private static void setNetworkAddress() {
        NetworkSettings settings = new NetworkSettings();
        port = settings.getScreenPortNumber();
        inetAddressName = settings.getScreenNetworkInetAddress();
        networkInterfaceName = settings.getScreenNetworkInterfaceName();
    }

    public static boolean joinNetwork()
    {
        setNetworkAddress();

        try{
            group = InetAddress.getByName(inetAddressName);
            socket = new MulticastSocket(port);

            socket.setTimeToLive(1);
            if(socket.isClosed())
            {
                return false;
            }
            socket.joinGroup(new InetSocketAddress(group , port) , NetworkInterface.getByName(networkInterfaceName));

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void endConnectedNetwork()
    {
        if(!socket.isClosed())
        {
            socket.close();
        }
    }
    public static byte[] receiver()
    {
        try
        {
            complete = new byte[0] ;
            while(!(socket.isClosed()))
            {
                byte[] bytes = new byte[1024];
                packet = new DatagramPacket(bytes , bytes.length , group , port);

                socket.receive(packet);
                byte[] imgData = packet.getData();
                int packetLength = packet.getLength();
                byte[] newImageData = new byte[complete.length + packetLength];

                System.arraycopy(complete,0, newImageData,0,complete.length);
                System.arraycopy(imgData , 0, newImageData,complete.length, packetLength);
                complete = newImageData;

                if(packetLength <1024 || socket.isClosed())
                {
                    break;
                }
            }
            System.out.println(count++);
            return complete;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}