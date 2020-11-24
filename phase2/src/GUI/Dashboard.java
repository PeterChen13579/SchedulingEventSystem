package GUI;

import Controllers.TechConferenceSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class Dashboard{

    private static JFrame frame;
    private final JPanel buttonPanel;
    private JButton load;
    private JButton newConference;
    private JButton login;
    private JButton createAttendee;
    private JButton createOrganizer;
    private JButton back;
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
    private JButton confirmEventSignup;
    private JButton confirmEventRemoval;
    private JButton confirmFriend;
    private JButton nextPanel;
    private JButton confirmFilename;
    private JButton save;
    private JButton confirmSave;
    private JButton confirmRoomNumber;
    private JButton confirmOneMessage;
    private JButton confirmAllMessage;
    private JButton confirmAddEvent;
    private JLabel addRoomLabel;
    private JTextField textInput;
    private JTextField roomNumber;
    private JTextField roomCapacity;
    private JPasswordField password;
    private String currentMenu;
    private String previousMenu;
    private String loginType;
    private JLabel displayCapacity;
    private Viewable sendsInfo;
    private JTextField filename;
    private JLabel errorText;
    private JLabel displayUsername, displayPassword;
    private JList displayList;
    private String currentUsername, currentPassword;
    private SynthLookAndFeel style = new SynthLookAndFeel();

    public Dashboard() {
        //@peter dw about this lmao
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
        currentMenu = "";
        previousMenu = "";
        loginType = "";
        loadMenu();
    }

//---------------------------------------Loading conference to Main Menu ---------------------------------------;

    private void loadMenu() {
        currentMenu = "LoadOrNewConference";
        buttonPanel.removeAll();
        buttonPanel.add(load);
        buttonPanel.add(newConference);
        frame.pack();
    }

    private void loadConference(){
        currentMenu = "LoadingConference";
        buttonPanel.removeAll();
        buttonPanel.add(filename);
        buttonPanel.add(confirmFilename);
        buttonPanel.add(back);
        frame.pack();
    }



//---------------------------------------Main Menu Features/options ---------------------------------------;


    private void loginSignup() {
        currentMenu = "LoginSignup";
        buttonPanel.removeAll();
        buttonPanel.add(login);
        buttonPanel.add(createOrganizer);
        buttonPanel.add(save);
        buttonPanel.add(exit);
        frame.pack();
    }


    private void usernamePassword() {
        currentMenu = "UsernamePassword";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(textInput);
        buttonPanel.add(displayPassword);
        buttonPanel.add(password);
        buttonPanel.add(confirmLogIn);
        buttonPanel.add(back);
        frame.pack();
    }


    private void createAttendeeAccount(){
        currentMenu = "CreateAttendee";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(textInput);
        buttonPanel.add(displayPassword);
        buttonPanel.add(password);
        buttonPanel.add(confirmAttendeeSignUp);
        buttonPanel.add(back);
        frame.pack();
    }


    private void createOrganizerAccount(){
        currentMenu = "CreateOrganizer";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(textInput);
        buttonPanel.add(displayPassword);
        buttonPanel.add(password);
        buttonPanel.add(confirmOrganizerSignUp);
        buttonPanel.add(back);
        frame.pack();
    }


    private void createSpeakerAccount(){
        System.out.println("Create Speaker");
        currentMenu = "CreateSpeaker";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(textInput);
        buttonPanel.add(displayPassword);
        buttonPanel.add(password);
        buttonPanel.add(confirmSpeakerSignUp);
        buttonPanel.add(back);
        frame.pack();
    }

    private void saveMenu() {
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmSave);
        frame.pack();
    }



//---------------------------------------LoggedInUsers ---------------------------------------;


    private void loggedInAttendee() {
        System.out.println("this happened");
        loginType = "Attendee";
        currentMenu = "LoggedInAttendee";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }

    private void loggedInOrganizer() {
        loginType = "Organizer";
        currentMenu = "LoggedInOrganizer";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(scheduleMenu);
        buttonPanel.add(createSpeaker);
        buttonPanel.add(createAttendee);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }

    private void loggedInSpeaker() {
        loginType = "Speaker";
        currentMenu = "LoggedInSpeaker";
        buttonPanel.removeAll();
        buttonPanel.add(messageMenu);
        buttonPanel.add(seeListEvents);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        frame.pack();
    }

//---------------------------------------Event Menu ---------------------------------------;


    private void signUpEventMenu() {
        currentMenu = "SignUpEvent";
        buttonPanel.removeAll();
        buttonPanel.add(browseEvent);
        buttonPanel.add(signUpEvent);
        buttonPanel.add(cancelAttendEvent);
        buttonPanel.add(back);
        frame.pack();
    }

    private void browseEventMenu() {
        currentMenu = "BrowseEvent";
        buttonPanel.removeAll();
        buttonPanel.add(seeAllEvent);
        buttonPanel.add(seeSignedEvent);
        buttonPanel.add(back);
        frame.pack();
    }


    private void displayEvents(boolean allOrNot) {
        currentMenu = "DisplayEvents";
        buttonPanel.removeAll();
        String[] info;
        if (allOrNot) {
            info = sendsInfo.displayAllEvents();
        } else {
            // save current username I guess to run this
            info = sendsInfo.displaySignedUpEvents(currentUsername);
        }
        if (info.length == 0) {
            errorText.setText("no events :(");
            buttonPanel.add(errorText);
        } else {
            displayList.setListData(info);
            buttonPanel.add(displayList);
        }
        buttonPanel.add(back);
        frame.pack();
    }

    private void signUpForEvent() {
        currentMenu = "SignUpForEvent";
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmEventSignup);
        buttonPanel.add(back);
        frame.pack();
    }

    private void cancelAttendEvent() {
        currentMenu = "CancelAttendEvent";
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmEventRemoval);
        buttonPanel.add(back);
        frame.pack();
    }


//---------------------------------------Messaging Menu ---------------------------------------;
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
        //TODO: fill in chats, formatted, prob using JList or JScrollPane or w/e
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


//---------------------------------------Scheduling Menu ---------------------------------------;

    private void schedulingMenu() {
        currentMenu = "Scheduling";
        buttonPanel.removeAll();
        buttonPanel.add(addRoom);
        buttonPanel.add(addEvent);
        buttonPanel.add(back);
        frame.pack();
    }

    private void addRoom(){
        currentMenu = "AddRoom";
        buttonPanel.removeAll();
        buttonPanel.add(addRoomLabel);
        buttonPanel.add(roomNumber);
        buttonPanel.add(displayCapacity);
        buttonPanel.add(roomCapacity);
        buttonPanel.add(confirmRoomNumber);
        buttonPanel.add(back);
        frame.pack();
    }

    private void addEvent() {
        currentMenu = "AddEvent";
        buttonPanel.removeAll();
        //TODO: implement adding an event (there are 5 text fields im too smol brain for that rn)
        buttonPanel.add(confirmAddEvent);
        buttonPanel.add(back);
        frame.pack();
    }

//---------------------------------------Failed Menu ---------------------------------------;

    private void failedMenu(String failedMessage) {
        buttonPanel.removeAll();
        errorText.setText(failedMessage);
        buttonPanel.add(nextPanel);
        buttonPanel.add(errorText);
        frame.pack();
    }

    private void createButtons() {
        load = new JButton("Load Existing Conference");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoadOrNewConference";
                loadConference();
            }
        });
        load.setName("button");
        newConference = new JButton("Create New Conference");
        newConference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginSignup();
                //Add techconferenceSystem to check it loads successfully or not
            }
        });
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoginSignup";
                usernamePassword();
            }
        });
        createAttendee = new JButton("Create Attendee Account");
        createAttendee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                createAttendeeAccount();
            }
        });
        createOrganizer = new JButton("Create Organizer Account");
        createOrganizer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoginSignup";
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
                previousMenu = "LoggedIn";
                signUpEventMenu();
            }
        });
        messageMenu = new JButton("Messaging Menu");
        messageMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
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
                previousMenu = "SignUpMenu";
                browseEventMenu();
            }
        });
        signUpEvent = new JButton("Sign Up for Event");
        signUpEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SignUpMenu";
                signUpForEvent();
            }
        });
        cancelAttendEvent = new JButton("Cancel Attendance to Event");
        cancelAttendEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SignUpMenu";
                cancelAttendEvent();
            }
        });
        seeAllEvent = new JButton("See All Events");
        seeAllEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "BrowseMenu";
                displayEvents(true);
            }
        });
        seeSignedEvent = new JButton("See Signed Up Events");
        seeSignedEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "BrowseMenu";
                displayEvents(false);
            }
        });
        nextPanel = new JButton("Next");
        nextPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToSameMenu();
            }
        });
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu();
            }
        });
        viewChat = new JButton("View All Chats");
        viewChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "MessageMenu";
                chatDisplay();
            }
        });
        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "MessageMenu";
                System.out.println("bruh?");
                sendMessageMenu();
            }
        });
        viewNewMessages = new JButton("View New Messages");
        viewNewMessages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "MessageMenu";
                displayNewMessages();
            }
        });
        addFriend = new JButton("Add Friend");
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "MessageMenu";
                addFriend();
            }
        });
        scheduleMenu = new JButton("Schedule Menu");
        scheduleMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                schedulingMenu();
            }
        });
        createSpeaker = new JButton("Create Speaker Account");
        createSpeaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                createSpeakerAccount();
            }
        });
        addRoom = new JButton("Add Room");
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ScheduleMenu";
                addRoom();
            }
        });
        addEvent = new JButton("Add Event");
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ScheduleMenu";
                addEvent();
            }
        });
        seeListEvents = new JButton("See List of Events");
        seeListEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                displayEvents(false);
            }
        });
        sendOne = new JButton("Send To One User");
        sendOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SendMessageMenu";
                sendOneMessage();
            }
        });
        sendAll = new JButton("Send To All Users");
        sendAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SendMessageMenu";
                sendAllEventMessage();
            }
        });
        confirmRoomNumber = new JButton("Confirm");
        confirmRoomNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int capacity = tryParse(roomCapacity.getText());
                if (capacity != -1){
                    if (sendsInfo.confirmRoom(roomNumber.getText(), capacity)) {
                        loggedInOrganizer();
                    }
                }else{
                    failedMenu("You have entered an invalid room number or the room " +
                            "has already been created");
                }
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
                    loggedInOrganizer();
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
                    loggedInOrganizer();
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
                currentUsername = textInput.getText();
                String status = sendsInfo.LogInButton(currentUsername, password.getText());
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
                returnToSameMenu();
            }
        });
        confirmEventSignup = new JButton("Confirm");
        confirmEventSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SignUpMenu";
                int result = sendsInfo.signUpForEvent(currentUsername, textInput.getText());
                switch (result) {
                    case 0:
                        previousMenu();
                    case 1:
                        failedMenu("You have signed up for this event before.");
                    case 2:
                        failedMenu("The event you have entered is already full.");
                    case 3:
                        failedMenu("The event title you have entered is invalid.");
                }
                clearTextField();
            }
        });
        confirmEventRemoval = new JButton("Confirm");
        confirmEventRemoval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SignUpMenu";
                int result = sendsInfo.cancelAttendEvent(currentUsername, textInput.getText());
                switch (result) {
                    case 0:
                        previousMenu();
                        break;
                    case 1:
                        failedMenu("You haven't signed up for this event yet.");
                        break;
                    case 2:
                        failedMenu("The event title you have entered is invalid.");
                        break;
                }
                clearTextField();
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
        confirmAddEvent = new JButton("Confirm");
        confirmAddEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 add event
            }
        });
        textInput = new JTextField(15);
        password = new JPasswordField(15);
        errorText = new JLabel();
        displayList = new JList();
        filename = new JTextField("File Name", 15);
        roomNumber = new JTextField(15);
        roomCapacity = new JTextField(15);
        addRoomLabel = new JLabel("Room Number:");
        displayCapacity = new JLabel("Capacity");
        displayUsername = new JLabel("Username");
        displayPassword = new JLabel("Password:");
    }

    public void setView(final Viewable sendsInfo) {
        this.sendsInfo = sendsInfo;
    }

    private void clearTextField() {
        textInput.setText("");
        password.setText("");
    }

    private void previousMenu() {
        switch (previousMenu) {
            case "LoggedIn":
                loginType();
                break;
            case "LoadOrNewConference":
                loadMenu();
                break;
            case "LoginSignup":
                loginSignup();
                break;
            case "SignUpMenu":
                signUpEventMenu();
                previousMenu = "LoggedIn";
                break;
            case "BrowseMenu":
                browseEventMenu();
                previousMenu = "SignUpMenu";
                break;
            case "MessageMenu":
                messagingMenu();
                previousMenu = "LoggedIn";
                break;
            case "ScheduleMenu":
                schedulingMenu();
                previousMenu = "LoggedIn";
                break;
            case "SendMessageMenu":
                sendMessageMenu();
                previousMenu = "MessageMenu";
                break;
        }
    }

    private void returnToSameMenu() {
        switch (currentMenu) {
            case "CreateSpeaker":
                createSpeakerAccount();
                break;
            case "CreateAttendee":
                createAttendeeAccount();
                break;
            case "LoggedInAttendee":
                loggedInAttendee();
                break;
            case "LoggedInOrganizer":
                loggedInOrganizer();
                break;
            case "LoggedInSpeaker":
                loggedInSpeaker();
                break;
            case "LoginSignup":
                loginSignup();
                break;
            case "LoadingConference":
                loadMenu();
                break;
            case "AddRoom":
                addRoom();
                break;
            case "CreateOrganizer":
                createOrganizerAccount();
                break;
            case "UsernamePassword":
                usernamePassword();
                break;
            case "SignUpForEvent":
                signUpForEvent();
                break;
            case "CancelAttendEvent":
                cancelAttendEvent();
                break;
        }
    }

    private void loginType() {
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
    }

    public Integer tryParse(String text){
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}