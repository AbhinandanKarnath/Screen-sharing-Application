package com.project.model;

public class NetworkSettings implements Network{
    private static int screenPort = 5550;                                                                                     // Default network settings
    private static int chatPort = 1234;
    private static String screenInetAddress = "225.0.0.0";
    private static String chatInetAddress = "226.0.0.0";
    private static String screenInterfaceName = "summit";
    private static String chatInterfaceName = "summitMessage";

    @Override                                                                                                           // user-defined network settings
    public void setScreenPortNumber(int port) {
        this.screenPort = port ;
    }

    @Override
    public void setChatPortNumber(int port) {
        this.chatPort = port ;
    }

    @Override
    public void setScreenNetworkInetAddress(String address) {
        this.screenInetAddress = address ;
    }

    @Override
    public void setChatNetworkInetAddress(String address) {
        this.chatInetAddress = address ;
    }

    @Override
    public void setScreenNetworkInterfaceName(String addressName) {
        this.screenInterfaceName =addressName;
    }

    @Override
    public void setChatNetworkInterfaceName(String addressName) {
        this.chatInterfaceName = addressName;
    }

    @Override
    public int getScreenPortNumber() {
        return screenPort;
    }

    @Override
    public int getChatPortNumber() {
        return chatPort;
    }

    @Override
    public String getScreenNetworkInetAddress() {
        return screenInetAddress;
    }

    @Override
    public String getChatNetworkInetAddress() {
        return chatInetAddress;
    }

    @Override
    public String getScreenNetworkInterfaceName() {
        return screenInterfaceName;
    }

    @Override
    public String getChatNetworkInterfaceName() {
        return chatInterfaceName;
    }
}