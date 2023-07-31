package com.project.model;

public interface Network {
     void setScreenPortNumber(int port);
     void setChatPortNumber(int port);

     void setScreenNetworkInetAddress(String address);
     void setChatNetworkInetAddress(String address);
     void setScreenNetworkInterfaceName(String addressName);
     void setChatNetworkInterfaceName(String addressName);
     int getScreenPortNumber();
     int getChatPortNumber();
     String getScreenNetworkInetAddress();
     String getChatNetworkInetAddress();
     String getScreenNetworkInterfaceName();
     String getChatNetworkInterfaceName();

}
