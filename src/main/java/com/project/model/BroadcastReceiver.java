package com.project.model;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.net.*;

public class BroadcastReceiver {
    int count = 0 ;
    InetAddress group;
    MulticastSocket socket;
    public boolean joinNetwork()
    {
        try{
            group = InetAddress.getByName("225.0.0.0");
            socket = new MulticastSocket(5000);

            socket.setTimeToLive(0);
            if(socket.isClosed())
            {
                return false;
            }
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("summit"));
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
        socket.close();
    }
    public byte[] receiver()
    {
        try
        {
            byte[] complete = new byte[0] ;
            int i=0;
            while(i<108  || !(socket.isClosed()))
            {
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes , bytes.length , group , 5000);

                socket.receive(packet);
                byte[] imgData = packet.getData();
                byte[] newImagedata = new byte[complete.length + packet.getLength()];

                System.arraycopy(complete,0,newImagedata,0,complete.length);
                System.arraycopy(imgData , 0,newImagedata,complete.length,packet.getLength());
                complete = newImagedata;

                if(packet.getLength()<1024 || socket.isClosed())
                {
                    break;
                }
            }
            return complete;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
