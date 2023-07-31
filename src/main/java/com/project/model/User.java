package com.project.model;

public class User implements UserInterface{
    private String userName;
    private String userDesignation;
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
}
