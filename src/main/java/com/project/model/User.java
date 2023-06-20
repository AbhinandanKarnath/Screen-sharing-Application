package com.project.model;

public class User implements UserInterface,Network{
    private String userName;
    private String userDesignation;
    private int port;
    private String address;
    private String addressName;
    static User user;
    public void setUserObject(User user)
    {
        this.user = user;
    }
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setDesignation(String userDesignation) {
        this.userDesignation =  userDesignation;
    }
    public static User getUserObject()
    {
        return user;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getDesignation() {
        return userDesignation;
    }

    @Override
    public void setPortNumber(int port) {
        this.port = port;
    }

    @Override
    public void setNetworkInetAddress(String address) {
        this.address = address;
    }

    @Override
    public void setNetworkInterfaceName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public int getPortNumber() {
        return port;
    }

    @Override
    public String getNetworkInetAddress() {
        return address;
    }

    @Override
    public String getNetworkInterfaceName() {
        return addressName;
    }
}
