package com.project.main;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.net.*;

public class BroadcastReceiver {
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
                System.out.println("network not seted....");
                return false;
            }
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("summit"));
            System.out.println("network seted....");
            return true;
        }
        catch (Exception e)
        {
            System.out.println("network not seted....");
            System.out.println(e);
            return false;
        }
    }
    public void endConnectedNetwork()
    {
        socket.close();
    }
    public Image receiver()
    {
        try
        {
            byte[] complete = new byte[0] ;


            int i=0;
            System.out.println(complete.length);
            while(i<108  || !(socket.isClosed()))
            {
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes , bytes.length , group , 5000);

                socket.receive(packet);

                byte[] imgData = packet.getData();

                byte[] newImagedata = new byte[complete.length + packet.getLength()];

//                StringBuilder b = new StringBuilder(Arrays.toString(imgData));
//                System.out.println(b);
                System.arraycopy(complete,0,newImagedata,0,complete.length);
                System.arraycopy(imgData , 0,newImagedata,complete.length,packet.getLength());
                complete = newImagedata;

                if(packet.getLength()<1024 || socket.isClosed())
                {
                    break;
                }
            }

//            for(int j = 0 ; j<complete.length/5 ; j++)
//            {
//                System.out.print(complete[j]);
//            }

            ByteArrayInputStream bi = new ByteArrayInputStream(complete);

            Image image = new Image(bi);
            return image;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
