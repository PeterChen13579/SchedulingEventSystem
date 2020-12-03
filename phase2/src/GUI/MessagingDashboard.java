package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.ArrayList;

public class MessagingDashboard extends JPanel{
    private final String loginType;
    private String currentMenu;
    private final String currentUsername;
    private JButton viewChat, sendMessage, viewNewMessages;
    private JButton addFriend,confirmFriend;
    private JButton sendOne,sendAllAttendee, sendAllSpeaker, sendAllAttendeeEvent;
    private JButton confirmOneMessage,confirmChatNumber;
    private JButton allAttendeeMsg, allSpeakerMsg, allEventMsg;
    private JButton nextPanel, back;
    private JButton deleteMsg;
    private JLabel errorText;
    private JLabel displayUsername, usernameLabel, msgContentLabel;
    private JList chatMsg;
    private String[] chatMsgIds;
    private JTextField friendAddText;
    private JTextField chatNumber;
    private JTextField usernameTextfield;
    private JTextField content;
    private JLabel displayChatNumber;
    private final Viewable sendsInfo;
    private ArrayList <String> userToDisplay;
    private final Dashboard dashboard;

    public MessagingDashboard(Viewable sendsInfo, Dashboard dashboard, String currentUsername, String loginType){

        this.sendsInfo = sendsInfo;
        this.currentUsername = currentUsername;
        this.loginType = loginType;
        this.dashboard = dashboard;
        userToDisplay = sendsInfo.sendChatName(currentUsername);
        try {
            SynthLookAndFeel style = new SynthLookAndFeel();
            style.load(Dashboard.class.getResourceAsStream("sadness.xml"), Dashboard.class);
            UIManager.setLookAndFeel(style);
        } catch (Exception e) {
            System.out.println(e);
        }
        createButtons();
        messagingMenu();
    }


    private void messagingMenu() {
        currentMenu = "Messaging";
        this.removeAll();
        this.add(viewChat);
        this.add(sendMessage);
        this.add(viewNewMessages);
        this.add(addFriend);
        this.add(back);
        dashboard.refresh();
    }

    private void sendMessageMenu() {
        currentMenu = "SendMessage";
        this.removeAll();
        this.add(sendOne);
        if (loginType.equals("Organizer")) {
            this.add(sendAllAttendee);
            this.add(sendAllSpeaker);
        }else if (loginType.equals("Speaker")){
            this.add(sendAllAttendeeEvent);
        }
        this.add(back);
        dashboard.refresh();
    }

    //TODO: fill in
    private void sendOneMessage() {
        currentMenu = "SendOneMessage";
        this.removeAll();
        this.add(usernameLabel);
        this.add(usernameTextfield);
        this.add(msgContentLabel);
        this.add(content);
        this.add(confirmOneMessage);
        this.add(back);
        dashboard.refresh();
    }

    private void sendAllAttendee(){
        currentMenu = "MsgAllAttendees";
        this.removeAll();
        this.add(msgContentLabel);
        this.add(content);
        this.add(allAttendeeMsg);
        this.add(back);
        dashboard.refresh();
    }

    private void sendAllSpeaker(){
        currentMenu = "MsgAllSpeakers";
        this.removeAll();
        this.add(msgContentLabel);
        this.add(content);
        this.add(allSpeakerMsg);
        this.add(back);
        dashboard.refresh();
    }

    private void sendAllAttendeeEvent(){
        currentMenu = "MsgAllAttendeeEvent";
        this.removeAll();
        this.add(msgContentLabel);
        this.add(content);
        this.add(allEventMsg);
        this.add(back);
        dashboard.refresh();
    }


    private void chatDisplay(ArrayList <String> userToDisplay) {
        for (String s : userToDisplay) {
            JLabel addUsernameLabel = new JLabel(s);
            this.add(addUsernameLabel);
        }
        currentMenu = "ViewChat";
        this.removeAll();
        this.add(displayChatNumber);
        this.add(chatNumber);
        this.add(confirmChatNumber);
        this.add(back);
        dashboard.refresh();
    }

    private void displayChatMsg(String[][] messages){
        currentMenu = "ViewOneChat";
        this.removeAll();
        //chatMsg.setText(chatMsgDisplay);
        chatMsgIds = new String[messages.length];
        String[] formattedMessages = new String[messages.length];
        for (int i=0; i<messages.length; i++) {
            chatMsgIds[i] = messages[i][0];
            formattedMessages[i] = "[" + messages[i][3] + "] " + messages[i][1] + ": " + messages[i][0];
        }
        chatMsg = new JList(formattedMessages);
        this.add(chatMsg);
        this.add(back);
        dashboard.refresh();
    }

    private void displayNewMessages(String newMsgs) {
        currentMenu="ViewNewMessage";
        this.removeAll();
        //chatMsg.setText(newMsgs);
        //this.add(chatMsg);
        this.add(back);
        dashboard.refresh();
    }

    private void addFriend() {
        currentMenu="AddFriend";
        this.removeAll();
        this.add(displayUsername);
        this.add(friendAddText);
        this.add(confirmFriend);
        this.add(back);
        dashboard.refresh();
    }

    private void failedMenu(String failedMessage) {
        this.removeAll();
        errorText.setText(failedMessage);
        this.add(errorText);
        this.add(nextPanel);
        dashboard.refresh();
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
                //String newMsgs = sendsInfo.getNewMessages(currentUsername);
                //displayNewMessages(newMsgs);
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
                System.out.println(friendAddText.getText());
                String result = sendsInfo.addFriend(currentUsername, friendAddText.getText());
                if (result.equals("true")){
                    messagingMenu();
                    userToDisplay = sendsInfo.sendChatName(currentUsername);
                }else{
                    String failedMsg = result;
                    failedMenu(failedMsg);
                }
            }
        });
        confirmOneMessage = new JButton("Confirm");
        confirmOneMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = sendsInfo.sendOneMsg(currentUsername,usernameTextfield.getText(), usernameTextfield.getText(), "");
                if (result.equals("Message sent.")){
                    messagingMenu();
                }else{
                    failedMenu(result);
                }
            }
        });
        allAttendeeMsg = new JButton("Confirm");
        allAttendeeMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllAttendees(currentUsername,content.getText(), "");
                messagingMenu();
            }
        });
        allSpeakerMsg = new JButton("Confirm");
        allSpeakerMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllSpeakers(currentUsername, content.getText(), "");
                messagingMenu();
            }
        });
        allEventMsg = new JButton("Confirm");
        allEventMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.msgAllAttendeeEvent(currentUsername, new ArrayList<String>(), content.getText(), "");
                messagingMenu();
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
                    if (sendsInfo.viewChat(possibleNum, currentMenu) == null){
                        failedMenu("The integer you entered is invalid");
                    }else{
                        String[][] msgInfo = sendsInfo.viewChat(possibleNum, currentMenu);
                        displayChatMsg(msgInfo);
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
        chatMsg = new JList();
    }


    private void previousMenu() {
        switch (currentMenu) {
            case "Messaging":
                dashboard.backToMain("Msg");
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
