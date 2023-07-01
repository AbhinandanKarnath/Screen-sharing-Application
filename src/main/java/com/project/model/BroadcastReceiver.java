package com.project.model;

import java.net.*;

public class BroadcastReceiver {
    private static MulticastSocket socket;
    private InetAddress group;
    private DatagramPacket packet;
    private byte[] complete;
    private int port=5000;
    private int packetLength;
    private int count = 0 ;
    public boolean joinNetwork()
    {
        try{
            group = InetAddress.getByName("225.0.0.0");
            socket = new MulticastSocket(port);

            socket.setTimeToLive(0);
            if(socket.isClosed())
            {
                return false;
            }
            socket.joinGroup(new InetSocketAddress(group , port) , NetworkInterface.getByName("summit"));
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    public void endConnectedNetwork()
    {
        if(!socket.isClosed())
        {
            socket.close();
        }
    }
    public byte[] receiver()
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
                packetLength = packet.getLength();
                byte[] newImagedata = new byte[complete.length + packetLength];

                System.arraycopy(complete,0,newImagedata,0,complete.length);
                System.arraycopy(imgData , 0,newImagedata,complete.length,packetLength);
                complete = newImagedata;

                if(packetLength<1024 || socket.isClosed())
                {
                    break;
                }
            }
            System.out.println(count++);
            return complete;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}