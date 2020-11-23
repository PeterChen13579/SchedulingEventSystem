package GUI;

import Controllers.TechConferenceSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

public class Dashboard{

    private static JFrame frame;
    private JPanel buttonPanel;
    private JButton load;
    private JButton newConference;
    private JButton login;
    private JButton createAttendee;
    private JButton createOrganizer;
    private JButton quit;
    private JButton signUpMenu;
    private JButton messageMenu;
    private JButton logout;
    private JButton browseEvent;
    private JButton signUpEvent;
    private JButton cancelAttendEvent;
    private JButton seeAllEvent;
    private JButton seeSignedEvent;
    private JButton exit;
    private JButton viewChat;
    private JButton sendMessage;
    private JButton viewNewMessages;
    private JButton addFriend;
    private JButton scheduleMenu;
    private JButton createSpeaker;
    private JButton addRoom;
    private JButton addEvent;
    private JButton seeListEvents;
    private JButton sendOne;
    private JButton sendAll;
    private JButton confirmAttendeeSignUp;
    private JButton confirmOrganizerSignUp;
    private JButton confirmSpeakerSignUp;
    private JButton confirmLogIn;
    private JButton nextPanel;
    private JButton confirmFilename;
    private JButton save;
    private JButton confirmSave;
    private JTextField textInput;
    private JPasswordField password;
    private String currentMenu;
    private String previousMenu;
    private String loginType;
    private Viewable sendsInfo;
    private JTextField filename;
    private JLabel errorText;
    private JList displayList;

    public Dashboard() {
        frame = new JFrame("Tech Conference System");
        frame.setLayout(new CardLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        buttonPanel = new JPanel();
        frame.add(buttonPanel);
        frame.setVisible(true);
        createButtons();
        currentMenu = "";
        previousMenu = "";
        loginType = "";
        loadMenu();
    }

    public void loadMenu() {
        currentMenu = "load";
        buttonPanel.removeAll();
        buttonPanel.add(load);
        buttonPanel.add(newConference);
        frame.setBounds(300, 130, 800, 600);
        frame.getContentPane().setBackground(Color.BLUE);
    }

    public void loadConference(){
        currentMenu = "Loading Conference";
        buttonPanel.removeAll();
        buttonPanel.add(filename);
        buttonPanel.add(confirmFilename);
        frame.pack();
    }

    public void loginSignup() {
        currentMenu = "LoginSignUp";
        buttonPanel.removeAll();
        buttonPanel.add(login);
        buttonPanel.add(createAttendee);
        buttonPanel.add(createOrganizer);
        buttonPanel.add(save);
        buttonPanel.add(exit);
        frame.pack();
    }

    public void createAttendeeAccount(){
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(password);
        buttonPanel.add(confirmAttendeeSignUp);
        frame.pack();
    }


    public void createOrganizerAccount(){
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(password);
        buttonPanel.add(confirmOrganizerSignUp);
        frame.pack();
    }


    public void createSpeakerAccount(){
        currentMenu = "CreateSpeaker";
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(password);
        buttonPanel.add(confirmOrganizerSignUp);
        frame.pack();
    }

    public void failedMenu(String failedMessage) {
        currentMenu = "FailedMenu";
        buttonPanel.removeAll();
        errorText.setText(failedMessage);
        buttonPanel.add(nextPanel);
        buttonPanel.add(errorText);
        frame.pack();
    }

    public void loggedInAttendee() {
        loginType = "Attendee";
        currentMenu = "loggedinattendee";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }

    public void loggedInOrganizer() {
        loginType = "Organizer";
        currentMenu = "loggedinorganizer";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(scheduleMenu);
        buttonPanel.add(createSpeaker);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }

    public void loggedInSpeaker() {
        loginType = "Speaker";
        currentMenu = "loggedinspeaker";
        buttonPanel.removeAll();
        buttonPanel.add(messageMenu);
        buttonPanel.add(seeListEvents);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }


    public void signUpEventMenu() {
        currentMenu = "signupevent";
        buttonPanel.removeAll();
        buttonPanel.add(browseEvent);
        buttonPanel.add(signUpEvent);
        buttonPanel.add(cancelAttendEvent);
        buttonPanel.add(quit);
        frame.pack();
    }

    public void messagingMenu() {
        currentMenu = "messaging";
        buttonPanel.removeAll();
        buttonPanel.add(viewChat);
        buttonPanel.add(sendMessage);
        buttonPanel.add(viewNewMessages);
        buttonPanel.add(addFriend);
        buttonPanel.add(quit);
        frame.pack();
    }

    public void browseEventMenu() {
        currentMenu = "browseevent";
        buttonPanel.removeAll();
        buttonPanel.add(seeAllEvent);
        buttonPanel.add(seeSignedEvent);
        buttonPanel.add(quit);
        frame.pack();
    }


    public void schedulingMenu() {
        currentMenu = "scheduling";
        buttonPanel.removeAll();
        buttonPanel.add(addRoom);
        buttonPanel.add(addEvent);
        buttonPanel.add(quit);
        frame.pack();
    }


    public void sendMessageMenu() {
        currentMenu = "sendmessage";
        buttonPanel.removeAll();
        buttonPanel.add(sendOne);
        buttonPanel.add(sendAll);
        buttonPanel.add(quit);
        frame.pack();
    }

    public void usernamePassword() {
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(password);
        buttonPanel.add(confirmLogIn);
        frame.pack();
    }

    public void displayEvents(boolean allOrNot) {
        buttonPanel.removeAll();
        String[] info;
        if (allOrNot) {
            info = sendsInfo.displayAllEvents();
        } else {
            // save current username I guess to run this
//            info = sendsInfo.displaySignedUpEvents();
            info = new String[1];
        }
        if (info.length == 0) {
            errorText.setText("no events :(");
            buttonPanel.add(errorText);
        } else {
            displayList.setListData(info);
            buttonPanel.add(displayList);
        }
        frame.pack();
    }

    public void saveMenu() {
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmSave);
        frame.pack();
    }

    private void createButtons() {
        load = new JButton("Load Existing Conference");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadConference();
            }
        });
        newConference = new JButton("CreateNewConference");
        newConference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginSignup();
            }
        });
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMenu = "login";
                usernamePassword();
            }
        });
        createAttendee = new JButton("Create Attendee Account");
        createAttendee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAttendeeAccount();
            }
        });
        createOrganizer = new JButton("Create Organizer Account");
        createOrganizer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrganizerAccount();
            }
        });
        exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.saveProgram("hi.txt.");
                System.exit(0);
            }
        });
        signUpMenu = new JButton("Sign Up Menu");
        signUpMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpEventMenu();
            }
        });
        messageMenu = new JButton("Messaging Menu");
        messageMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagingMenu();
            }
        });
        logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginSignup();
            }
        });
        browseEvent = new JButton("Browse Events");
        browseEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseEventMenu();
            }
        });
        signUpEvent = new JButton("Sign Up for Event");
        signUpEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some sign up menu
            }
        });
        cancelAttendEvent = new JButton("Cancel Attendance to Event");
        cancelAttendEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some cancel menu
            }
        });
        seeAllEvent = new JButton("See All Events");
        seeAllEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some event display
                displayEvents(true);
            }
        });
        seeSignedEvent = new JButton("See Signed Up Events");
        seeSignedEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some other event display
                displayEvents(false);
            }
        });
        nextPanel = new JButton("Next");
        nextPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(currentMenu){
                    case "Organizer":
                        loggedInOrganizer();
                        break;
                    case "LoginSignUp":
                        loginSignup();
                        break;
                }
            }
        });

        quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: move back one menu
                previousMenu();
            }
        });
        viewChat = new JButton("View All Chats");
        viewChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some chat display
            }
        });
        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some send message display
            }
        });
        viewNewMessages = new JButton("View New Messages");
        viewNewMessages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some view new message display
            }
        });
        addFriend = new JButton("Add Friend");
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some add friend display
            }
        });
        scheduleMenu = new JButton("Schedule Menu");
        scheduleMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schedulingMenu();
            }
        });
        createSpeaker = new JButton("Create Speaker Account");
        createSpeaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSpeakerAccount();
            }
        });
        addRoom = new JButton("Add Room");
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add room adding display
            }
        });
        addEvent = new JButton("Add Event");
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add event adding display
            }
        });
        seeListEvents = new JButton("See List of Events");
        seeListEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add list of events display
            }
        });
        sendOne = new JButton("Send To One User");
        sendOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: send to one user display
            }
        });
        sendAll = new JButton("Send To All Users");
        sendAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: send to all user display
            }
        });
        confirmAttendeeSignUp = new JButton("Confirm");
        confirmAttendeeSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fyi password.getText() is deprecated bc java prefers password.getPassword()
                //which returns a char array instead of a string. idk if anyone wants to change
                //user/pass verification to use char array for the password but i'm not doing it so
                if (sendsInfo.createAttendeeButton(textInput.getText(), password.getText())) {
                    loginSignup();
                    System.out.println("hi2");
                }else{
                    failedMenu("The existing username is in our database. Please try again.");
                    System.out.println("hi3");
                }
                clearTextField();
            }
        });
        confirmOrganizerSignUp = new JButton("Confirm");
        confirmOrganizerSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.createOrganizerButton(textInput.getText(), password.getText())) {
                    loginSignup();
                    System.out.println("hi2");
                }else{
                    failedMenu("The existing username is in our database. Please try again.");
                    System.out.println("hi3");
                }
                clearTextField();
            }
        });
        confirmSpeakerSignUp = new JButton("Confirm");
        confirmSpeakerSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.createSpeakerButton(textInput.getText(), password.getText())) {
                    loginSignup();
                }else{
                    failedMenu("The existing username is in our database. Please try again.");
                }
                clearTextField();
            }
        });
        confirmLogIn = new JButton("Confirm Log In");
        confirmLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = sendsInfo.LogInButton(textInput.getText(), password.getText());
                switch (status) {
                    case "false":
                        failedMenu("You have entered an invalid username/password or " +
                                "the username does not exist in the database.");
                        break;
                    case "Attendee":
                        loggedInAttendee();
                        break;
                    case "Organizer":
                        loggedInOrganizer();
                        break;
                    case "Speaker":
                        loggedInSpeaker();
                        break;
                }
                clearTextField();
            }
        });
        confirmFilename = new JButton("Confirm");
        confirmFilename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String desiredFile = filename.getText();
                if (sendsInfo.loadConferenceButton(desiredFile)) {
                    loginSignup();
                } else {
                    failedMenu("Load failed.");
                }

            }
        });
        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMenu();
            }
        });
        confirmSave = new JButton("Confirm");
        confirmSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.saveProgram(textInput.getText());
                clearTextField();
                previousMenu();
            }
        });
        textInput = new JTextField("username", 20);
        password = new JPasswordField(20);
        errorText = new JLabel();
        displayList = new JList();
    }

    public void setView(final Viewable sendsInfo) {
        this.sendsInfo = sendsInfo;
    }

    private void clearTextField() {
        textInput.setText("");
        password.setText("");
    }

    private void previousMenu() {
        switch (currentMenu) {
            case "signupevent":
            case "messaging":
                switch (loginType) {
                    case "Attendee":
                        loggedInAttendee();
                        break;
                    case "Organizer":
                        loggedInOrganizer();
                        break;
                    case "Speaker":
                        loggedInSpeaker();
                        break;
                }
                break;
            case "browseevent":
                signUpEventMenu();
                break;
            case "scheduling":
                loggedInOrganizer();
                break;
            case "sendmessage":
                messagingMenu();
                break;
            case "loggedinattendee":
                loggedInAttendee();
                break;
            case "loggedinorganizer":
                loggedInOrganizer();
                break;
            case "loggedinspeaker":
                loggedInSpeaker();
                break;
            case "LoginSignUp":
                loginSignup();
                break;
        }
    }
}