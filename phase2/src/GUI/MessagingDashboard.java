package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class MessagingDashboard {
    private static JFrame frame;
    private String loginType;
    private String currentMenu;
    private String currentUsername;
    private final JPanel buttonPanel;
    private JButton viewChat;
    private JButton sendMessage;
    private JButton viewNewMessages;
    private JButton addFriend;
    private JButton confirmFriend;
    private JButton sendOne;
    private JButton sendAll;
    private JButton confirmOneMessage;
    private JButton confirmAllMessage;
    private JButton confirmChatNumber;
    private JButton back;
    private JTextField textInput;
    private JTextField chatNumber;
    private JLabel displayChatNumber;
    private SynthLookAndFeel style = new SynthLookAndFeel();
    private Viewable sendsInfo;

    public MessagingDashboard(Viewable sendsInfo, String currentUsername, String loginType){
        this.sendsInfo = sendsInfo;
        this.currentUsername = currentUsername;
        this.loginType = loginType;
        try {
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
        sendOne.setText("Send One");
        // make sure to block users that aren't speakers from sending to all eventually
        buttonPanel.add(sendOne);
        buttonPanel.add(sendAll);
        buttonPanel.add(back);
        frame.pack();
    }

    //TODO: fill in
    private void sendOneMessage() {
        buttonPanel.removeAll();
        buttonPanel.add(confirmOneMessage);
        buttonPanel.add(back);
        frame.pack();
    }

    //TODO: fill in, will most likely be just sendOneMessage repeated I guess
    private void sendAllEventMessage() {
        buttonPanel.removeAll();
        buttonPanel.add(confirmAllMessage);
        buttonPanel.add(back);
        frame.pack();
    }

    private void chatDisplay() {
        buttonPanel.removeAll();
        buttonPanel.add(displayChatNumber);
        buttonPanel.add(chatNumber);
        buttonPanel.add(confirmChatNumber);
        buttonPanel.add(back);
        frame.pack();
    }

    private void displayNewMessages() {
        buttonPanel.removeAll();
        //TODO: same as chatDisplay
        buttonPanel.add(back);
        frame.pack();
    }

    private void addFriend() {
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmFriend);
        buttonPanel.add(back);
        frame.pack();
    }

    private void createButtons(){
        viewChat = new JButton("View All Chats");
        viewChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatDisplay();
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
                displayNewMessages();
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
        sendAll = new JButton("Send To All Users");
        sendAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAllEventMessage();
            }
        });
        confirmFriend = new JButton("Confirm");
        confirmFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 add friend
            }
        });
        confirmOneMessage = new JButton("Confirm");
        confirmOneMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 send one message
            }
        });
        confirmAllMessage = new JButton("Confirm");
        confirmAllMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 send all message
            }
        });
        confirmChatNumber = new JButton("Confirm");
        confirmChatNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 send all message
            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu();
            }
        });
        textInput = new JTextField();
        chatNumber = new JTextField();
        displayChatNumber = new JLabel("Chat number:");
    }


    private void previousMenu() {
        switch (currentMenu) {
            case "Messaging":
                frame.dispose();
                new Dashboard(loginType, currentUsername);
            case "MessageMenu":
                messagingMenu();
                break;
            case "SendMessageMenu":
                sendMessageMenu();
                break;
        }
    }
}
