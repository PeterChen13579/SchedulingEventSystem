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
    private JButton exit;
    private JButton scheduleMenu;
    private JButton createSpeaker;
    private JButton addRoom;
    private JButton addEvent;
    private JButton cancelEvent;
    private JButton changeCapacity;
    private JButton seeListEvents;
    private JButton confirmAttendeeSignUp;
    private JButton confirmOrganizerSignUp;
    private JButton confirmSpeakerSignUp;
    private JButton confirmLogIn;
    private JButton confirmEventSignup;
    private JButton confirmEventRemoval;
    private JButton nextPanel;
    private JButton confirmFilename;
    private JButton save;
    private JButton confirmSave;
    private JButton confirmRoomNumber;
    private JButton confirmAddEvent;
    private JButton oneSpeakerEvent;
    private JButton multiSpeakerEvent;
    private JButton noSpeakerEvent;
    private JButton confirmCancelEvent;
    private JButton confirmChangeCapacity;
    private JTextField cancelEventTextfield;
    private JTextField changeCapacityEventTextfield;
    private JLabel addRoomLabel;
    private JTextField textInput;
    private JTextField roomNumber;
    private JTextField roomCapacity;
    private JTextField filename;
    private JTextField eventName;
    private JPasswordField password;
    private String currentMenu;
    private String previousMenu;
    private String loginType;
    private JLabel displayCapacity;
    private JLabel eventNameMsg;
    private Viewable sendsInfo;
    private JLabel errorText;
    private JLabel displayUsername, displayPassword;
    private JLabel cancelEventMsg, changeCapacityMsg;
    private JList displayList;
    private String currentUsername;
    private JLabel speakerNameDisplay, timeDisplay;
    private SignUpDashboard signUpDashboard;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Dashboard() {
        try {
            SynthLookAndFeel style = new SynthLookAndFeel();
            style.load(Dashboard.class.getResourceAsStream("sadness.xml"), Dashboard.class);
            UIManager.setLookAndFeel(style);
        } catch (Exception e) {
            System.out.println(e);
        }

        frame = new JFrame("Tech Conference System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.setLocation(screenSize.width/2 - 640, screenSize.height/2 - 360);
        buttonPanel = new JPanel();
        frame.add(buttonPanel);
        frame.setVisible(true);
        createButtons();
        currentMenu = "";
        previousMenu = "";
        loginType = "";
        loadMenu();
    }

    public Dashboard(Viewable sendsInfo, String loginType, String currentUsername){
        this.sendsInfo = sendsInfo;
        this.loginType = loginType;
        this.currentUsername = currentUsername;
        frame = new JFrame("Tech Conference System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        buttonPanel = new JPanel();
        frame.add(buttonPanel);
        frame.setVisible(true);
        createButtons();
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


//---------------------------------------Loading conference to Main Menu ---------------------------------------;

    private void loadMenu() {
        currentMenu = "LoadOrNewConference";
        buttonPanel.removeAll();
        buttonPanel.add(load);
        buttonPanel.add(newConference);
        refresh();
        
    }

    private void loadConference(){
        currentMenu = "LoadingConference";
        buttonPanel.removeAll();
        buttonPanel.add(filename);
        buttonPanel.add(confirmFilename);
        buttonPanel.add(back);
        refresh();
        
    }



//---------------------------------------Main Menu Features/options ---------------------------------------;


    private void loginSignup() {
        currentMenu = "LoginSignup";
        buttonPanel.removeAll();
        buttonPanel.add(login);
        buttonPanel.add(createOrganizer);
        buttonPanel.add(save);
        buttonPanel.add(exit);
        refresh();
        
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
        refresh();
        
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
        refresh();
        
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
        refresh();
        
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
        refresh();
        
    }

    private void saveMenu() {
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmSave);
        refresh();
        
    }



//---------------------------------------LoggedInUsers ---------------------------------------;


    public void loggedInAttendee() {
        System.out.println("this happened");
        loginType = "Attendee";
        currentMenu = "LoggedInAttendee";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        refresh();
        
    }

    public void loggedInOrganizer() {
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
        refresh();
        
    }

    public void loggedInSpeaker() {
        loginType = "Speaker";
        currentMenu = "LoggedInSpeaker";
        buttonPanel.removeAll();
        buttonPanel.add(messageMenu);
        buttonPanel.add(seeListEvents);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        refresh();
        
    }

//---------------------------------------Sign up Event Menu ---------------------------------------;


    private void signUpEventMenu() {
        signUpDashboard = new SignUpDashboard(sendsInfo, currentUsername, this);
        frame.remove(buttonPanel);
        frame.add(signUpDashboard);
        refresh();
    }




//---------------------------------------Messaging Menu ---------------------------------------;
    private void messagingMenu() {
        buttonPanel.removeAll();
        frame.dispose();
        new MessagingDashboard(sendsInfo, currentUsername, loginType);
    }


//---------------------------------------Scheduling Menu ---------------------------------------;

    private void schedulingMenu() {
        currentMenu = "Scheduling";
        buttonPanel.removeAll();
        buttonPanel.add(addRoom);
        buttonPanel.add(addEvent);
        buttonPanel.add(cancelEvent);
        buttonPanel.add(changeCapacity);
        buttonPanel.add(back);
        refresh();
        
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
        refresh();
        
    }

    private void addEvent() {
        currentMenu = "ChooseEvent";
        buttonPanel.removeAll();
        buttonPanel.add(oneSpeakerEvent);
        buttonPanel.add(multiSpeakerEvent);
        buttonPanel.add(noSpeakerEvent);
        buttonPanel.add(back);
        refresh();
        
    }

    private void cancelEvent(){
        currentMenu = "CancelEvent";
        buttonPanel.removeAll();;
        buttonPanel.add(cancelEventMsg);
        buttonPanel.add(cancelEventTextfield);
        buttonPanel.add(confirmCancelEvent);
        buttonPanel.add(back);
        refresh();
        
    }

    private void changeEventCapacity(){
        currentMenu = "changeEventCapacity";
        buttonPanel.removeAll();
        buttonPanel.add(eventNameMsg);
        buttonPanel.add(eventName);
        buttonPanel.add(changeCapacityMsg);
        buttonPanel.add(changeCapacityEventTextfield);
        buttonPanel.add(confirmChangeCapacity);
        buttonPanel.add(back);
        refresh();
        
    }

    private void createEvent(String eventType){
        currentMenu = "CreateEvent";
        buttonPanel.removeAll();
        if (eventType.equals("one")){

        }
        buttonPanel.add(back);
        refresh();
        

    }

//---------------------------------------Failed Menu ---------------------------------------;

    private void failedMenu(String failedMessage) {
        buttonPanel.removeAll();
        errorText.setText(failedMessage);
        buttonPanel.add(errorText);
        buttonPanel.add(nextPanel);
        refresh();
        
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
        addEvent = new JButton("Add New Event");
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ScheduleMenu";
                addEvent();
            }
        });
        cancelEvent = new JButton("Cancel Event");
        cancelEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ScheduleMenu";
                cancelEvent();
            }
        });
        changeCapacity = new JButton("Change Capacity of Event");
        changeCapacity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ScheduleMenu";
                changeEventCapacity();
            }
        });
        confirmCancelEvent = new JButton("Cancel Event");
        confirmCancelEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendsInfo.cancelEvent(cancelEventTextfield.getText())){
                    schedulingMenu();
                }else{
                    failedMenu("The event you have entered does not exist.");
                }
            }
        });
        confirmChangeCapacity = new JButton("Confirm");
        confirmChangeCapacity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int changeCapacity = tryParse(changeCapacityEventTextfield.getText());
                if (changeCapacity != -1){
                    sendsInfo.changeCapacity(eventName.getText(), changeCapacity);
                    changeEventCapacity();
                }else{
                    failedMenu("You must enter an integer.");
                }
            }
        });
        oneSpeakerEvent = new JButton("One-Speaker Event");
        oneSpeakerEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ChooseEvent";
                createEvent("one");
            }
        });
        multiSpeakerEvent = new JButton("Multi-Speaker Event");
        multiSpeakerEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ChooseEvent";
                createEvent("multi");
            }
        });
        noSpeakerEvent = new JButton("No-Speaker Event");
        noSpeakerEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "ChooseEvent";
                createEvent("no");
            }
        });
        //TODO: move this somewhere
        seeListEvents = new JButton("See List of Events");
        seeListEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                //displayEvents(false);
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
                    }else{
                        failedMenu("This room has already been created.");
                    }
                }else{
                    failedMenu("You have entered an invalid room number");
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

        confirmAddEvent = new JButton("Confirm");
        confirmAddEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: connect phase 1 add event
            }
        });
        textInput = new JTextField(12);
        password = new JPasswordField(12);
        errorText = new JLabel();
        displayList = new JList();
        filename = new JTextField("File Name", 12);
        roomNumber = new JTextField(12);
        roomCapacity = new JTextField(12);
        eventName = new JTextField(12);
        changeCapacityEventTextfield = new JTextField(12);
        cancelEventTextfield = new JTextField(12);
        addRoomLabel = new JLabel("Room Number:");
        displayCapacity = new JLabel("Capacity:");
        displayUsername = new JLabel("Username:");
        displayPassword = new JLabel("Password:");
        changeCapacityMsg = new JLabel("New Capacity:");
        cancelEventMsg = new JLabel("Event name:");
        eventNameMsg = new JLabel("Event name:");

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
            case "MessageMenu":
                messagingMenu();
                previousMenu = "LoggedIn";
                break;
            case "ScheduleMenu":
                schedulingMenu();
                previousMenu = "LoggedIn";
                break;
            case "CreateEvent":
                schedulingMenu();
            case "ChooseEvent":
                addEvent();
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

    private Integer tryParse(String text){
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void backToMain() {
        frame.remove(signUpDashboard);
        frame.add(buttonPanel);
        refresh();
        loginType();
    }

    public void refresh() {
        frame.pack();
        frame.repaint();
    }
}