package com.project.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.model.NetworkSettings;

public class NetworkController implements Initializable {
    Alert alert;
    @FXML TextField chatPort;
    @FXML TextField screenPort;
    @FXML TextField chatInetAddress1;
    @FXML TextField chatInetAddress2;
    @FXML TextField chatInetAddress3;
    @FXML TextField chatInetAddress4;
    @FXML TextField screenInetAddress1;
    @FXML TextField screenInetAddress2;
    @FXML TextField screenInetAddress3;
    @FXML TextField screenInetAddress4;
    @FXML TextField chatInterfaceName;
    @FXML TextField screenInterfaceName;
    NetworkSettings networkSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        networkSettings = new NetworkSettings();
        String inetChat = networkSettings.getChatNetworkInetAddress();
        String inetScreen = networkSettings.getScreenNetworkInetAddress();
        String[] inetChatPart = inetChat.split("\\.",4);
        String[] inetScreenPart = inetScreen.split("\\.",4);

        chatInetAddress1.setText(inetChatPart[0]);
        chatInetAddress2.setText(inetChatPart[1]);
        chatInetAddress3.setText(inetChatPart[2]);
        chatInetAddress4.setText(inetChatPart[3]);
        screenInetAddress1.setText(inetScreenPart[0]);
        screenInetAddress2.setText(inetScreenPart[1]);
        screenInetAddress3.setText(inetScreenPart[2]);
        screenInetAddress4.setText(inetScreenPart[3]);
        chatPort.setText(String.valueOf(networkSettings.getChatPortNumber()));
        chatInterfaceName.setText(networkSettings.getChatNetworkInterfaceName());
        screenPort.setText(String.valueOf(networkSettings.getScreenPortNumber()));
        screenInterfaceName.setText(networkSettings.getScreenNetworkInterfaceName());
    }
    public static  void checkPortNumber(int port)throws NumberFormatException
    {
        if((port < 1023) || (port > 49152))
        {
            throw new NumberFormatException("port number '"+port+"' is either Invalid or reserved");
        }
    }

    private static void checkInetAddress(int A , int B , int C , int D) throws NumberFormatException{
        if(A < 224 || A > 239 )
        {
            throw new NumberFormatException("Invalid Inet Address number in "+A);
        }
        if( B < 0 || B > 255)
        {
            throw new NumberFormatException("Invalid Inet Address number in "+B);
        }
        if(C < 0 || C > 255)
        {
            throw new NumberFormatException("Invalid Inet Address number in "+C);
        }
        if(D < 0 || D > 255)
        {
            throw new NumberFormatException("Invalid Inet Address number in "+D);
        }
    }

    private static void checkNetworkInterface(String screen , String chat)throws NumberFormatException
    {
        if(screen.equals("") || chat.equals(""))
        {
            throw new NumberFormatException("Null networkInterface Name");
        }
    }
    private static void checkSame(String chat , String screen)throws NumberFormatException
    {
        if(chat.equals(screen))
        {
            throw new NumberFormatException("The Network setting should not be same");
        }
    }
    @FXML
    public void clearAll()
    {
        String inetChat = networkSettings.getChatNetworkInetAddress();
        String inetScreen = networkSettings.getScreenNetworkInetAddress();
        String[] inetChatPart = inetChat.split("\\.",4);
        String[] inetScreenPart = inetScreen.split("\\.",4);

        chatInetAddress1.setText(inetChatPart[0]);
        chatInetAddress2.setText(inetChatPart[1]);
        chatInetAddress3.setText(inetChatPart[2]);
        chatInetAddress4.setText(inetChatPart[3]);
        screenInetAddress1.setText(inetScreenPart[0]);
        screenInetAddress2.setText(inetScreenPart[1]);
        screenInetAddress3.setText(inetScreenPart[2]);
        screenInetAddress4.setText(inetScreenPart[3]);
        chatPort.setText(String.valueOf(networkSettings.getChatPortNumber()));
        chatInterfaceName.setText(networkSettings.getChatNetworkInterfaceName());
        screenPort.setText(String.valueOf(networkSettings.getScreenPortNumber()));
        screenInterfaceName.setText(networkSettings.getScreenNetworkInterfaceName());
    }

    @FXML
    public void validate()
    {
        try {
            int intChatPortNumber = Integer.parseInt(chatPort.getText());
            int intScreenPortNumber = Integer.parseInt(screenPort.getText());

            int intScreenInetAddress1 = Integer.parseInt(screenInetAddress1.getText());
            int intScreenInetAddress2 = Integer.parseInt(screenInetAddress2.getText());
            int intScreenInetAddress3 = Integer.parseInt(screenInetAddress3.getText());
            int intScreenInetAddress4 = Integer.parseInt(screenInetAddress4.getText());

            int intChatInetAddress1 = Integer.parseInt(chatInetAddress1.getText());
            int intChatInetAddress2 = Integer.parseInt(chatInetAddress2.getText());
            int intChatInetAddress3 = Integer.parseInt(chatInetAddress3.getText());
            int intChatInetAddress4 = Integer.parseInt(chatInetAddress4.getText());

            String stringChatInterfaceName = chatInterfaceName.getText();
            String stringScreenInterfaceName = screenInterfaceName.getText();

            checkPortNumber(intChatPortNumber);
            checkPortNumber(intScreenPortNumber);
            checkInetAddress(intChatInetAddress1, intChatInetAddress2, intChatInetAddress3, intChatInetAddress4);
            checkInetAddress(intScreenInetAddress1, intScreenInetAddress2, intScreenInetAddress3, intScreenInetAddress4);
            checkNetworkInterface(stringScreenInterfaceName, stringChatInterfaceName);

            String chat = intChatInetAddress1 +"."+ intChatInetAddress2 +"."+ intChatInetAddress3 +"."+ intChatInetAddress4;
            String screen = intScreenInetAddress1 +"."+ intScreenInetAddress2 +"."+ intScreenInetAddress3 +"."+ intScreenInetAddress4;

            checkSame(String.valueOf(intChatPortNumber) , String.valueOf(intScreenPortNumber));
            checkSame(stringChatInterfaceName, stringScreenInterfaceName);
            checkSame(chat , screen);

            saveChanges(intChatPortNumber, chat , stringChatInterfaceName, intScreenPortNumber, screen , stringScreenInterfaceName);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("summit network settings");
            alert.setHeaderText("settings saved");
            alert.show();
        }
        catch (NumberFormatException invalid)
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Network settings Error");
            alert.setHeaderText(invalid.getMessage());
            alert.setContentText("Enter correct format input in the TextField");
            alert.showAndWait();
        }
        catch (Exception e)
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("an error occurred");
            alert.showAndWait();
        }
    }

    private void saveChanges(int chatPort , String chatInet , String chatNetworkInterface , int screenPort , String screenInet , String screenNetworkInterface)
    {
        networkSettings.setChatPortNumber(chatPort);
        networkSettings.setChatNetworkInetAddress(chatInet);
        networkSettings.setChatNetworkInterfaceName(chatNetworkInterface);

        networkSettings.setScreenPortNumber(screenPort);
        networkSettings.setScreenNetworkInetAddress(screenInet);
        networkSettings.setScreenNetworkInterfaceName(screenNetworkInterface);
    }
}