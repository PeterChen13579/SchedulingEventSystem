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
    private JButton confirmOneMessage,confirmChatNumber, archiveChat, markChatUnread;
    private JButton allAttendeeMsg, allSpeakerMsg, allEventMsg;
    private JButton nextPanel, back;
    private JButton deleteMsg;
    private JLabel errorText;
    private JLabel displayUsername, usernameLabel, msgContentLabel, newMessages;
    private JList chatNames, chatMsg;
    private int currentChatIndex;
    private JTextField friendAddText;
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
        this.currentChatIndex = -1;
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


    private void chatDisplay() {
        currentMenu = "ViewChat";
        this.removeAll();
        this.add(displayChatNumber);
        this.add(confirmChatNumber);
        this.add(archiveChat);
        this.add(markChatUnread);
        this.add(back);
        userToDisplay = sendsInfo.sendChatName(currentUsername);

        chatNames = new JList<String>(userToDisplay.toArray(new String[userToDisplay.size()]));
        chatNames.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        chatNames.setLayoutOrientation(JList.VERTICAL);
        chatNames.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(chatNames);
        listScroller.setPreferredSize(new Dimension(750, 800));
        this.add(listScroller);
        dashboard.refresh();
    }

    private void displayChatMsg(String[][] messages){
        currentMenu = "ViewOneChat";
        this.removeAll();
        String[] formattedMessages = new String[messages.length];
        for (int i=0; i<messages.length; i++) {
            formattedMessages[i] = "          " + messages[i][0] + " :    " + messages[i][1] +
                    String.format("%1$" + (70 + ("["+ messages[i][2]+"]").length()) + "s", "["+messages[i][2]+"]");
        }
        chatMsg = new JList<String>(formattedMessages);
        chatMsg.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        chatMsg.setLayoutOrientation(JList.VERTICAL);
        chatMsg.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(chatMsg);
        listScroller.setPreferredSize(new Dimension(750, 800));
        this.add(listScroller);
        this.add(deleteMsg);
        this.add(back);
        dashboard.refresh();
    }

    private void displayNewMessages(String newMessagesString) {
        currentMenu="ViewNewMessage";
        this.removeAll();
        newMessages.setText(newMessagesString);
        this.add(newMessages);
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
        confirmChatNumber = new JButton("View");
        confirmChatNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chatNum = chatNames.getSelectedIndex();
                currentChatIndex = chatNum;
                if (chatNum == -1) {
                    failedMenu("Please select a chat.");
                } else {
                    String[][] msgInfo = sendsInfo.viewChat(chatNum+1, currentUsername);
                    displayChatMsg(msgInfo);
                }

            }
        });
        archiveChat = new JButton("Archive");
        archiveChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chatNum = chatNames.getSelectedIndex();
                if (chatNum == -1) {
                    failedMenu("Please select a chat.");
                } else {
                    sendsInfo.archiveChats(currentUsername, chatNum);
                    chatDisplay();
                }

            }
        });
        markChatUnread = new JButton("Mark as unread");
        markChatUnread.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chatNum = chatNames.getSelectedIndex();
                if (chatNum == -1) {
                    failedMenu("Please select a chat.");
                } else {
                    sendsInfo.markChatAsUnread(currentUsername, chatNum);
                    chatDisplay();
                }

            }
        });
        deleteMsg = new JButton("Delete selected message");
        deleteMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int messageNum = chatNames.getSelectedIndex();
                if (messageNum == -1) {
                    failedMenu("Please select a message to delete.");
                } else {
                    sendsInfo.deleteMsg(currentUsername, currentChatIndex, messageNum);
                    String[][] msgInfo = sendsInfo.viewChat(currentChatIndex+1, currentUsername);
                    displayChatMsg(msgInfo);
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
                        chatDisplay();

                }
            }
        });
        friendAddText = new JTextField(12);
        usernameTextfield = new JTextField(12);
        content = new JTextField(12);
        displayChatNumber = new JLabel("Select a chat and choose an action:");
        usernameLabel = new JLabel("Recipient username");
        msgContentLabel = new JLabel("Msg");
        displayUsername = new JLabel("Username:");
        errorText = new JLabel();
        newMessages = new JLabel();
        chatMsg = new JList();
    }


    private void previousMenu() {
        switch (currentMenu) {
            case "Messaging":
                dashboard.backToMain("Msg");
                break;
            case "ViewOneChat":
                chatDisplay();
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
