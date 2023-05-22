package com.project.main;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.net.*;

public class BroadcastReceiver {
    public Image receiver()
    {
        try
        {
            InetAddress group = InetAddress.getByName("224.0.0.0");
            MulticastSocket socket = new MulticastSocket(5000);

            if(socket.isClosed())
            {
                return null;
            }
            socket.joinGroup(new InetSocketAddress(group , 5000) , NetworkInterface.getByName("localhost"));

            byte[] complete = new byte[0] ;


            int i=0;
            System.out.println(complete.length);
            while(i<108  || !(socket.isClosed()))
            {
                System.out.println(i++);
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
            System.out.println(complete.length);

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
