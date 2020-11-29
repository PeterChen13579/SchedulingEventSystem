package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.ArrayList;

public class MessagingDashboard {
    private static JFrame frame;
    private final String loginType;
    private String currentMenu;
    private final String currentUsername;
    private final JPanel buttonPanel;
    private JButton viewChat;
    private JButton sendMessage;
    private JButton viewNewMessages;
    private JButton addFriend;
    private JButton confirmFriend;
    private JButton sendOne;
    private JButton sendAllAttendee;
    private JButton sendAllSpeaker;
    private JButton sendAllAttendeeEvent;
    private JButton confirmOneMessage;
    private JButton confirmChatNumber;
    private JButton allAttendeeMsg;
    private JButton allSpeakerMsg;
    private JButton allEventMsg;
    private JButton back;
    private JButton nextPanel;
    private JLabel errorText;
    private JLabel displayUsername;
    private JLabel usernameLabel;
    private JLabel msgContentLabel;
    private JLabel chatMsg;
    private JTextField friendAddText;
    private JTextField chatNumber;
    private JTextField usernameTextfield;
    private JTextField content;
    private JLabel displayChatNumber;
    private final Viewable sendsInfo;
    private ArrayList <String> userToDisplay;

    public MessagingDashboard(Viewable sendsInfo, String currentUsername, String loginType){
        this.sendsInfo = sendsInfo;
        this.currentUsername = currentUsername;
        this.loginType = loginType;
        userToDisplay = sendsInfo.sendChatName();
        try {
            SynthLookAndFeel style = new SynthLookAndFeel();
            style.load(Dashboard.class.getResourceAsStream("sadness.xml"), Dashboard.class);
            UIManager.setLookAndFeel(style);
        } catch (Exception e) {
            System.out.println(e);
        }
        frame = new JFrame("Tech Conference System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        buttonPanel = new JPanel();
        frame.add(buttonPanel);
        frame.setVisible(true);
        createButtons();
        messagingMenu();
    }


    private void messagingMenu() {
        currentMenu = "Messaging";
        buttonPanel.removeAll();
        buttonPanel.add(viewChat);
        buttonPanel.add(sendMessage);
        buttonPanel.add(viewNewMessages);
        buttonPanel.add(addFriend);
        buttonPanel.add(back);
        frame.pack();
    }

    private void sendMessageMenu() {
        currentMenu = "SendMessage";
        buttonPanel.removeAll();
        buttonPanel.add(sendOne);
        if (loginType.equals("Organizer")) {
            buttonPanel.add(sendAllAttendee);
            buttonPanel.add(sendAllSpeaker);
        }else if (loginType.equals("Speaker")){
            buttonPanel.add(sendAllAttendeeEvent);
        }
        buttonPanel.add(back);
        frame.pack();
    }

    //TODO: fill in
    private void sendOneMessage() {
        currentMenu = "SendOneMessage";
        buttonPanel.removeAll();
        buttonPanel.add(usernameLabel);
        buttonPanel.add(usernameTextfield);
        buttonPanel.add(msgContentLabel);
        buttonPanel.add(content);
        buttonPanel.add(confirmOneMessage);
        buttonPanel.add(back);
        frame.pack();
    }

    private void sendAllAttendee(){
        currentMenu = "MsgAllAttendees";
        buttonPanel.removeAll();
        buttonPanel.add(msgContentLabel);
        buttonPanel.add(content);
        buttonPanel.add(allAttendeeMsg);
        buttonPanel.add(back);
        frame.pack();
    }

    private void sendAllSpeaker(){
        currentMenu = "MsgAllSpeakers";
        buttonPanel.removeAll();
        buttonPanel.add(msgContentLabel);
        buttonPanel.add(content);
        buttonPanel.add(allSpeakerMsg);
        buttonPanel.add(back);
        frame.pack();
    }

    private void sendAllAttendeeEvent(){
        currentMenu = "MsgAllAttendeeEvent";
        buttonPanel.removeAll();
        buttonPanel.add(msgContentLabel);
        buttonPanel.add(content);
        buttonPanel.add(allEventMsg);
        buttonPanel.add(back);
        frame.pack();
    }


    private void chatDisplay(ArrayList <String> userToDisplay) {
        for (String s : userToDisplay) {
            JLabel addUsernameLabel = new JLabel(s);
            buttonPanel.add(addUsernameLabel);
        }
        currentMenu = "ViewChat";
        buttonPanel.removeAll();
        buttonPanel.add(displayChatNumber);
        buttonPanel.add(chatNumber);
        buttonPanel.add(confirmChatNumber);
        buttonPanel.add(back);
        frame.pack();
    }

    private void displayChatMsg(String chatMsgDisplay){
        currentMenu = "ViewOneChat";
        buttonPanel.removeAll();
        chatMsg.setText(chatMsgDisplay);
        buttonPanel.add(chatMsg);
        buttonPanel.add(back);
        frame.pack();
    }

    private void displayNewMessages(String newMsgs) {
        currentMenu="ViewNewMessage";
        buttonPanel.removeAll();
        chatMsg.setText(newMsgs);
        buttonPanel.add(chatMsg);
        buttonPanel.add(back);
        frame.pack();
    }

    private void addFriend() {
        currentMenu="AddFriend";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(friendAddText);
        buttonPanel.add(confirmFriend);
        buttonPanel.add(back);
        frame.pack();
    }

    private void failedMenu(String failedMessage) {
        buttonPanel.removeAll();
        errorText.setText(failedMessage);
        buttonPanel.add(errorText);
        buttonPanel.add(nextPanel);
        frame.pack();
    }

    private void createButtons(){
        viewChat = new JButton("View Chats");
        viewChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatDisplay(userToDisplay);
            }
        });
        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bruh?");
                sendMessageMenu();
            }
        });
        viewNewMessages = new JButton("View New Messages");
        viewNewMessages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newMsgs = sendsInfo.getNewMessages();
                displayNewMessages(newMsgs);
            }
        });
        addFriend = new JButton("Add Friend");
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFriend();
            }
        });
        sendOne = new JButton("Send To One User");
        sendOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendOneMessage();
            }
        });
        sendAllAttendee = new JButton("Msg All Attendees");
        sendAllAttendee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAllAttendee();
            }
        });
        sendAllSpeaker = new JButton("Msg all Speaker");
        sendAllSpeaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAllSpeaker();
            }
        });
        sendAllAttendeeEvent = new JButton("Msg all Attendees at Event");
        sendAllAttendeeEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAllAttendeeEvent();
            }
        });
        confirmFriend = new JButton("Confirm");
        confirmFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.addFriend(friendAddText.getText()).equals("true")){
                    messagingMenu();
                    userToDisplay = sendsInfo.sendChatName();
                }else{
                    String failedMsg = sendsInfo.addFriend(friendAddText.getText());
                    failedMenu(failedMsg);
                }
            }
        });
        confirmOneMessage = new JButton("Confirm");
        confirmOneMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.sendOneMsg(currentUsername, usernameTextfield.getText())){
                    messagingMenu();
                }else{
                    failedMenu("Can not find specified username.");
                }
            }
        });
        allAttendeeMsg = new JButton("Confirm");
        allAttendeeMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllAttendees(content.getText());
            }
        });
        allSpeakerMsg = new JButton("Confirm");
        allSpeakerMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllSpeakers(content.getText());
            }
        });
        allEventMsg = new JButton("Confirm");
        allEventMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllAttendeeEvent(content.getText());
            }
        });
        confirmChatNumber = new JButton("Confirm");
        confirmChatNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int possibleNum = tryParse(chatNumber.getText());
                if (possibleNum == -1){
                    failedMenu("You must enter a valid integer");
                }else{
                    if (sendsInfo.viewChatMsg(possibleNum).equals("false")){
                        failedMenu("The integer you entered is invalid");
                    }else{
                        String displayMsg = sendsInfo.viewChatMsg(possibleNum);
                        displayChatMsg(displayMsg);
                    }
                }

            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu();
            }
        });
        nextPanel = new JButton("Next");
        nextPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentMenu){
                    case "SendOneMessage":
                        sendOneMessage();
                        break;
                    case "AddFriend":
                        addFriend();
                        break;
                    case "ViewChat":
                        chatDisplay(userToDisplay);

                }
            }
        });
        friendAddText = new JTextField(12);
        chatNumber = new JTextField(12);
        usernameTextfield = new JTextField(12);
        content = new JTextField(12);
        displayChatNumber = new JLabel("Chat number:");
        usernameLabel = new JLabel("Recipient username");
        msgContentLabel = new JLabel("Msg");
        displayUsername = new JLabel("Username:");
        errorText = new JLabel();
        chatMsg = new JLabel();
    }


    private void previousMenu() {
        switch (currentMenu) {
            case "Messaging":
                frame.dispose();
                new Dashboard(sendsInfo, loginType, currentUsername);
                break;
            case "ViewOneChat":
                chatDisplay(userToDisplay);
                break;
            case "ViewChat":
            case "SendMessage":
            case "ViewNewMessage":
            case "AddFriend":
                messagingMenu();
                break;
            default:
                sendMessageMenu();
                break;

        }
    }

    private Integer tryParse(String text){
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
