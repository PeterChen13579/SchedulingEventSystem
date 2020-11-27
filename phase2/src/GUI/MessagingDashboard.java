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
    private JButton sendAllAttendee;
    private JButton sendAllSpeaker;
    private JButton sendAllAttendeeEvent;
    private JButton confirmEventTitle;
    private JButton confirmOneMessage;
    private JButton confirmAllMessage;
    private JButton confirmChatNumber;
    private JButton back;
    private JLabel usernameLabel;
    private JLabel msgContentLabel;
    private JTextField textInput;
    private JTextField chatNumber;
    private JTextField usernameTextfield;
    private JTextField content;
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
        buttonPanel.removeAll();
        buttonPanel.add(back);
    }

    private void sendAllSpeaker(){
        buttonPanel.removeAll();
        buttonPanel.add(back);
    }

    private void sendAllAttendeeEvent(){
        buttonPanel.removeAll();
        buttonPanel.add(back);
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
        textInput = new JTextField(12);
        chatNumber = new JTextField(12);
        usernameTextfield = new JTextField(12);
        content = new JTextField(12);
        displayChatNumber = new JLabel("Chat number:");
        usernameLabel = new JLabel("Recipient username");
        msgContentLabel = new JLabel("Msg");
    }


    private void previousMenu() {
        switch (currentMenu) {
            case "Messaging":
                frame.dispose();
                new Dashboard(loginType, currentUsername);
                break;
            case "ViewChat":
            case "SendMessage":
            case "ViewNewMessage":
                messagingMenu();
                break;
            default:
                sendMessageMenu();

        }
    }
}
