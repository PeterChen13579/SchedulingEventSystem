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
    private JTextField username;
    private JPasswordField password;
    private String currentMenu;
    private String loginType;
    private Viewable sendsInfo;

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
        //TODO: TO PETER change loginType between attendee/organizer/speaker to test different logins
        loginType = "attendee";
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

    //To Joyce, w/e button you need to add to the load Conference, I'm not touching this.
    public void loadConference(){
        currentMenu = "Loading Conference";
        buttonPanel.removeAll();
        sendsInfo.loadConferenceButton();
        frame.pack();

    }

    public void loginSignup() {
        currentMenu = "LoginSignUp";
        buttonPanel.removeAll();
        buttonPanel.add(login);
        buttonPanel.add(createAttendee);
        buttonPanel.add(createOrganizer);
        buttonPanel.add(exit);
        frame.pack();
    }

    public void createAttendeeAccount(){
        buttonPanel.removeAll();
        buttonPanel.add(username);
        buttonPanel.add(password);
        buttonPanel.add(confirmAttendeeSignUp);
        frame.pack();
    }


    public void createOrganizerAccount(){
        buttonPanel.removeAll();
        buttonPanel.add(username);
        buttonPanel.add(password);
        buttonPanel.add(confirmOrganizerSignUp);
        frame.pack();
    }


    public void createSpeakerAccount(){
        currentMenu = "CreateSpeaker";
        buttonPanel.removeAll();
        buttonPanel.add(username);
        buttonPanel.add(password);
        buttonPanel.add(confirmOrganizerSignUp);
        frame.pack();
    }


    public void failedAccountLogin(){
        buttonPanel.removeAll();
        JLabel failedMsgOne = new JLabel ("You have entered an invalid username/password or");
        JLabel failedMsgTwo = new JLabel("the username does not exist in the database.");
        buttonPanel.add(nextPanel);
        buttonPanel.add(failedMsgOne);
        buttonPanel.add(failedMsgTwo);
        frame.pack();
    }

    public void failedAccountCreate(){
        buttonPanel.removeAll();
        JLabel failedMsgOne = new JLabel ("The existing username is in our database.");
        JLabel failedMsgTwo = new JLabel("Please try again");
        buttonPanel.add(nextPanel);
        buttonPanel.add(failedMsgOne);
        buttonPanel.add(failedMsgTwo);
        frame.pack();
    }



    public void loggedInAttendee() {
        currentMenu = "loggedinattendee";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
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

    public void loggedInOrganizer() {
        currentMenu = "loggedinorganizer";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(scheduleMenu);
        buttonPanel.add(createSpeaker);
        buttonPanel.add(logout);
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

    public void loggedInSpeaker() {
        currentMenu = "loggedinspeaker";
        buttonPanel.removeAll();
        buttonPanel.add(messageMenu);
        buttonPanel.add(seeListEvents);
        buttonPanel.add(logout);
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
        buttonPanel.add(username);
        buttonPanel.add(password);
        buttonPanel.add(confirmLogIn);
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
            }
        });
        seeSignedEvent = new JButton("See Signed Up Events");
        seeSignedEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: some other event display
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
                switch (currentMenu) {
                    case "signupevent":
                    case "messaging":
                        switch (loginType) {
                            case "attendee":
                                loggedInAttendee();
                                break;
                            case "organizer":
                                loggedInOrganizer();
                                break;
                            case "speaker":
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
                }
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
                if (sendsInfo.createAttendeeButton("username", "password")) {
                    loginSignup();
                    System.out.println("hi2");
                }else{
                    failedAccountCreate();
                    System.out.println("hi3");
                }
            }
        });
        confirmOrganizerSignUp = new JButton("Confirm");
        confirmOrganizerSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.createOrganizerButton("username", "password")) {
                    loginSignup();
                    System.out.println("hi2");
                }else{
                    failedAccountCreate();
                    System.out.println("hi3");
                }
            }
        });
        confirmSpeakerSignUp = new JButton("Confirm");
        confirmSpeakerSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.createSpeakerButton("username", "password")) {
                    loginSignup();
                }else{
                    failedAccountCreate();
                }
            }
        });
        confirmLogIn = new JButton("Confirm Log In");
        confirmLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = sendsInfo.LogInButton("username", "password");
                switch (status) {
                    case "false":
                        failedAccountLogin();
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
            }
        });
        username = new JTextField("username", 20);
        password = new JPasswordField(20);
    }

    public void setView(final Viewable sendsInfo) {
        this.sendsInfo = sendsInfo;
    }
}
