package com.project.model;

public interface Network {
     void setPortNumber(int port);
     void setNetworkInetAddress(String address);
     void setNetworkInterfaceName(String addressName);
     int getPortNumber();
     String getNetworkInetAddress();
     String getNetworkInterfaceName();

}
