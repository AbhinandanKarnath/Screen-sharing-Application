package com.project.controller;

import com.project.model.NetworkSettings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NetworkController implements Initializable {

    @FXML TextField screenPort;
    @FXML TextField chatPort;
    @FXML TextField screenInterfaceName;
    @FXML TextField chatInterfaceName;
    @FXML TextField screenInetAddress1;
    @FXML TextField screenInetAddress2;
    @FXML TextField screenInetAddress3;
    @FXML TextField screenInetAddress4;
    @FXML TextField chatInetAddress1;
    @FXML TextField chatInetAddress2;
    @FXML TextField chatInetAddress3;
    @FXML TextField chatInetAddress4;
    int intScreenPortNumber;
    int intChatPortNumber;
    int intScreenInetAddress1;
    int intScreenInetAddress2;
    int intScreenInetAddress3;
    int intScreenInetAddress4;

    int intChatInetAddress1;
    int intChatInetAddress2;
    int intChatInetAddress3;
    int intChatInetAddress4;
    String stringScreenInterfaceName;
    String stringChatInterfaceName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NetworkSettings networkSettings = new NetworkSettings();
        String inetScreen = networkSettings.getScreenNetworkInetAddress();
        String inetChat = networkSettings.getChatNetworkInetAddress();
        String[] inetScreenPart = inetScreen.split("\\.",4);
        String[] inetChatPart = inetChat.split("\\.",4);

        System.out.println(inetChat+" "+inetChatPart[0]+" "+inetChatPart[1]+" "+inetChatPart[2]+" <-----");

        chatPort.setText(String.valueOf(networkSettings.getChatPortNumber()));
        screenPort.setText(String.valueOf(networkSettings.getScreenPortNumber()));
        chatInetAddress1.setText("");
        chatInetAddress2.setText("");
        chatInetAddress3.setText("");
        chatInetAddress4.setText("");
        chatInterfaceName.setText(networkSettings.getChatNetworkInterfaceName());
        screenInetAddress1.setText(inetScreenPart[0]);
        screenInetAddress2.setText(inetScreenPart[1]);
        screenInetAddress3.setText(inetScreenPart[2]);
        screenInetAddress4.setText(inetScreenPart[3]);
        chatInetAddress1.setText(inetChatPart[0]);
        chatInetAddress2.setText(inetChatPart[1]);
        chatInetAddress3.setText(inetChatPart[2]);
        chatInetAddress4.setText(inetChatPart[3]);
        screenInterfaceName.setText(networkSettings.getScreenNetworkInterfaceName());
    }

    @FXML
    public void clearAll()
    {
            chatPort.setText("");
            screenPort.setText("");
            chatInetAddress1.setText("");
            chatInetAddress2.setText("");
            chatInetAddress3.setText("");
            chatInetAddress4.setText("");
            chatInterfaceName.setText("");
            screenInetAddress1.setText("");
            screenInetAddress2.setText("");
            screenInetAddress3.setText("");
            screenInetAddress4.setText("");
            screenInterfaceName.setText("");
    }
    @FXML
    public void validate()
    {
        try {
            intChatPortNumber = Integer.parseInt(chatPort.getText());
            intScreenPortNumber = Integer.parseInt(screenPort.getText());

            intScreenInetAddress1 = Integer.parseInt(screenInetAddress1.getText());
            intScreenInetAddress2 = Integer.parseInt(screenInetAddress2.getText());
            intScreenInetAddress3 = Integer.parseInt(screenInetAddress3.getText());
            intScreenInetAddress4 = Integer.parseInt(screenInetAddress4.getText());

            intChatInetAddress1 = Integer.parseInt(chatInetAddress1.getText());
            intChatInetAddress2 = Integer.parseInt(chatInetAddress2.getText());
            intChatInetAddress3 = Integer.parseInt(chatInetAddress3.getText());
            intChatInetAddress4 = Integer.parseInt(chatInetAddress4.getText());

            stringChatInterfaceName = screenInterfaceName.getText();
            stringScreenInterfaceName = screenInterfaceName.getText();

            if((intChatPortNumber < 1066 || intChatPortNumber > 48680) || (intScreenPortNumber < 1066 || intScreenPortNumber > 48680))
            {
                throw new NumberFormatException("Invalid port number.");
            }
        }
        catch (NumberFormatException invalid)
        {
            System.out.println(invalid);

            chatPort.setText("");
            screenPort.setText("");
            chatInetAddress1.setText("");
            chatInetAddress2.setText("");
            chatInetAddress3.setText("");
            chatInetAddress4.setText("");
            chatInterfaceName.setText("");
            screenInetAddress1.setText("");
            screenInetAddress2.setText("");
            screenInetAddress3.setText("");
            screenInetAddress4.setText("");
            screenInterfaceName.setText("");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void saveChanges()
    {

    }
}