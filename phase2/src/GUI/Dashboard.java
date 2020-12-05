package GUI;

import Controllers.TechConferenceSystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.List;

/**
 * Creates base GUI for the entire program
 * @author Joyce Huang, Peter Chen
 */
public class Dashboard{

    private static JFrame frame;
    private final JPanel buttonPanel;
    private JButton load, login, newConference;
    private JButton createAttendee, createAttendeeMainMenu, createOrganizer;
    private JButton back, signUpMenu, messageMenu, logout, exit;
    private JButton scheduleMenu, createSpeaker, addRoom, addEvent, cancelEvent;
    private JButton changeCapacity, seeListEvents;
    private JButton confirmAttendeeSignUp, confirmAttendeeSignUpMainMenu, confirmOrganizerSignUp ;
    private JButton confirmSpeakerSignUp, confirmLogIn;
    private JButton nextPanel,confirmFilename,save, confirmSave;
    private JButton confirmRoomNumber, confirmAddEvent;
    private JButton oneSpeakerEvent, multiSpeakerEvent, noSpeakerEvent;
    private JButton confirmCancelEvent, confirmChangeCapacity;
    private JButton successNextPanel;
    private JButton sendRequest, confirmSendRequest;
    private JButton seeRequests, viewAllRequests, tagRequest;
    private JButton addressed, pending;
    private JTextField cancelEventTextfield, whichEvent;
    private JTextField changeCapacityEventTextfield;
    private JLabel addRoomLabel;
    private JTextField textInput, roomNumber, roomCapacity, filename, eventName, eventCapacity;
    private JTextField date, startTime, endTime, speakerUsernameOne, speakerUsernameMulti, VIP;
    private JLabel dateDisplay, startTimeDisplay, endTimeDisplay, VIPDisplay;
    private JLabel speakerUsernameDisplayOne, speakerUsernameDisplayMulti;
    private JPasswordField password;
    private String currentMenu, previousMenu;
    private String loginType;
    private JLabel displayCapacity;
    private JLabel eventNameMsg;
    private Viewable sendsInfo;
    private JLabel errorText, successText;
    private JLabel displayUsername, displayPassword;
    private JLabel cancelEventMsg, changeCapacityMsg;
    private JList displayList;
    private String currentUsername;
    private SignUpDashboard signUpDashboard;
    private MessagingDashboard messagingDashboard;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private SynthLookAndFeel regularTheme, vipTheme;
    private JFileChooser fileChooser;

    /**
     * Constructor that creates and starts the program
     */
    public Dashboard() {
        createThemes();
        frame = new JFrame("Tech Conference System");
        changeTheme("regular");
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
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        loadMenu();
    }


//---------------------------------------Loading conference to Main Menu ---------------------------------------;

    private void loadMenu() {
        currentMenu = "LoadOrNewConference";
        buttonPanel.removeAll();
        buttonPanel.add(load);
        buttonPanel.add(newConference);
        refresh();
        
    }


//---------------------------------------Main Menu Features/options ---------------------------------------;


    private void loginSignup() {
        currentMenu = "LoginSignup";
        buttonPanel.removeAll();
        buttonPanel.add(login);
        buttonPanel.add(createOrganizer);
        buttonPanel.add(createAttendeeMainMenu);
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

    private void createAttendeeAccountMainMenu(){
        currentMenu = "CreateAttendeeMain";
        buttonPanel.removeAll();
        buttonPanel.add(displayUsername);
        buttonPanel.add(textInput);
        buttonPanel.add(displayPassword);
        buttonPanel.add(password);
        buttonPanel.add(confirmAttendeeSignUpMainMenu);
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


    private void loggedInAttendee() {
        loginType = "Attendee";
        currentMenu = "LoggedInAttendee";
        buttonPanel.removeAll();
        buttonPanel.add(signUpMenu);
        buttonPanel.add(messageMenu);
        buttonPanel.add(sendRequest);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        refresh();
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
        buttonPanel.add(seeRequests);
        buttonPanel.add(save);
        buttonPanel.add(logout);
        refresh();
    }

    private void loggedInSpeaker() {
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
        messagingDashboard = new MessagingDashboard(sendsInfo, this, currentUsername, loginType);
        frame.remove(buttonPanel);
        frame.add(messagingDashboard);
        refresh();
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
        speakerUsernameMulti.setText("");
        speakerUsernameOne.setText("");
        previousMenu = "ScheduleMenu";
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
        currentMenu = "changeEventCapacity" ;
        buttonPanel.removeAll();
        buttonPanel.add(eventNameMsg);
        buttonPanel.add(eventName);
        buttonPanel.add(changeCapacityMsg);
        buttonPanel.add(changeCapacityEventTextfield);
        buttonPanel.add(addRoomLabel);
        buttonPanel.add(roomNumber);
        buttonPanel.add(confirmChangeCapacity);
        buttonPanel.add(back);
        refresh();
    }

    private void createEvent(String eventType){
        currentMenu = "CreateEvent";
        buttonPanel.removeAll();
        buttonPanel.add(VIPDisplay);
        buttonPanel.add(VIP);
        buttonPanel.add(dateDisplay);
        buttonPanel.add(date);
        buttonPanel.add(startTimeDisplay);
        buttonPanel.add(startTime);
        buttonPanel.add(endTimeDisplay);
        buttonPanel.add(endTime);
        buttonPanel.add(eventNameMsg);
        buttonPanel.add(eventName);
        buttonPanel.add(addRoomLabel);
        buttonPanel.add(roomNumber);
        buttonPanel.add(displayCapacity);
        buttonPanel.add(eventCapacity);
        if (eventType.equals("one")){
            buttonPanel.add(speakerUsernameDisplayOne);
            buttonPanel.add(speakerUsernameOne);
            whichEvent.setText("one");
        }else if (eventType.equals("multi")){
            buttonPanel.add(speakerUsernameDisplayMulti);
            buttonPanel.add(speakerUsernameMulti);
            whichEvent.setText("multi");
        }
        buttonPanel.add(confirmAddEvent);
        buttonPanel.add(back);
        refresh();
    }

//---------------------------------------Send Request Menu ---------------------------------------;

    private void sendRequestMenu() {
        currentMenu = "SendRequest";
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(confirmSendRequest);
        buttonPanel.add(back);
        refresh();
    }

    private void seeRequestsMenu() {
        currentMenu = "SeeRequests";
        buttonPanel.removeAll();
        buttonPanel.add(viewAllRequests);
        buttonPanel.add(tagRequest);
        buttonPanel.add(back);
        refresh();
    }

    private void viewRequests() {
        currentMenu = "ViewRequests";
        buttonPanel.removeAll();
        buttonPanel.add(back);
        refresh();
    }

    private void tagRequest() {
        currentMenu = "TagRequest";
        buttonPanel.removeAll();
        buttonPanel.add(textInput);
        buttonPanel.add(addressed);
        buttonPanel.add(pending);
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

    private void successMenu(String successMessage){
        buttonPanel.removeAll();
        successText.setText(successMessage);
        buttonPanel.add(successText);
        buttonPanel.add(successNextPanel);
        refresh();
    }

    private void createButtons() {
        load = new JButton("Load Existing Conference");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoadOrNewConference";
                loadFile();
            }
        });
        load.setName("button");
        newConference = new JButton("Create New Conference");
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
        createAttendeeMainMenu = new JButton("Create Attendee Account");
        createAttendeeMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoginSignup";
                createAttendeeAccountMainMenu();
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
                changeTheme("regular");
                frame.setTitle("Tech Conference System");
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
        successNextPanel = new JButton("Next");
        successNextPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu();
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
                if (sendsInfo.cancelEvent(cancelEventTextfield.getText(), currentUsername)){
                    schedulingMenu();
                }else{
                    failedMenu("The event you have entered does not exist.");
                }
            }
        });
        confirmChangeCapacity = new JButton("Confirm");
        confirmChangeCapacity.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int changeCapacity = tryParse(changeCapacityEventTextfield.getText());
                if (changeCapacity != -1){
                    String changedSuccessfully = sendsInfo.changeCapacity(eventName.getText(), changeCapacity, currentUsername, roomNumber.getText());
                    if (changedSuccessfully.equals("true")){
                        schedulingMenu();
                    }else{
                        failedMenu(changedSuccessfully);
                    }
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
                createEvent("none");
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
                int roomNum = tryParse(roomNumber.getText());
                if (roomNum > -1){
                    if (capacity > 0) {
                        if (sendsInfo.confirmRoom(roomNumber.getText(), capacity)) {
                            successMenu("You have successfully created a room!");
                        } else {
                            failedMenu("This room has already been created.");
                        }
                    }else{
                        failedMenu("You must enter a valid event capacity");
                    }
                }else{
                    failedMenu("You have entered an invalid room number");
                }
                roomCapacity.setText("");
                roomNumber.setText("");
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
                }else{
                    failedMenu("The existing username is in our database. Please try again.");
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
                }else{
                    failedMenu("The existing username is in our database. Please try again.");
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
        confirmAttendeeSignUpMainMenu= new JButton("Confirm");
        confirmAttendeeSignUpMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu ="CreateAttendeeMain";
                if (sendsInfo.createAttendeeButton(textInput.getText(), password.getText())) {
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
                currentUsername = textInput.getText();
                String status = sendsInfo.LogInButton(currentUsername, password.getText());
                switch (status) {
                    case "false":
                        failedMenu("You have entered an invalid username/password or " +
                                "the username does not exist in the database.");
                        break;
                    case "Attendee":
                        loginType = "Attendee";
                        frame.setTitle("Attendee Account");
                        loginType();
                        break;
                    case "Organizer":
                        loginType = "Organizer";
                        frame.setTitle("Organizer Account");
                        loginType();
                        break;
                    case "Speaker":
                        loginType = "Speaker";
                        frame.setTitle("Speaker Account");
                        loginType();
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
//                saveMenu();
                saveFile();
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
                String vipVerify = VIP.getText();
                if (!(vipVerify.equalsIgnoreCase("yes") || vipVerify.equalsIgnoreCase("no"))){
                    failedMenu("You need to enter 'yes' or 'no' in vip");
                }else{
                    boolean vip;
                    vip = vipVerify.equalsIgnoreCase("yes");
                    List <String> speakerList = new ArrayList<String>();
                    int checkCapacity = tryParse(eventCapacity.getText());
                    if (checkCapacity <= 0){
                        failedMenu("Please enter a valid integer for Room Capacity.");
                    }else{
                        String createdOrNot = "";
                        if (speakerUsernameOne.getText().equals("") && speakerUsernameMulti.getText().equals("")){
                            if (!whichEvent.getText().equals("")){
                                whichEvent.setText("");
                                createdOrNot = "You must enter the correct number of speakers for your specified event";
                            }else{
                                createdOrNot = sendsInfo.createParty(vip, date.getText(), startTime.getText(),
                                        endTime.getText(), roomNumber.getText(), Collections.emptyList(), eventName.getText(),
                                        checkCapacity);
                            }
                        }else if(speakerUsernameMulti.getText().equals("")){
                            speakerList.add(speakerUsernameOne.getText());
                            createdOrNot = sendsInfo.createSpeakerEvent(vip, date.getText(), startTime.getText(), endTime.getText(),
                                    roomNumber.getText(), speakerList, eventName.getText(),checkCapacity);
                        }else if(speakerUsernameOne.getText().equals("")) {
                            String addSpeakerUsernames = speakerUsernameMulti.getText();
                            addSpeakerUsernames = addSpeakerUsernames.replaceAll("\\s+", "");
                            speakerList = Arrays.asList(addSpeakerUsernames.split(","));
                            createdOrNot = sendsInfo.createSpeakerEvent(vip, date.getText(), startTime.getText(), endTime.getText(),
                                    roomNumber.getText(), speakerList, eventName.getText(), checkCapacity);
                        }
                        if (createdOrNot.equals("true")){
                            successMenu("You have successfully created an event!");
                        }else{
                            failedMenu(createdOrNot);
                        }
                    }

                }
            }
        });
        sendRequest = new JButton("Send Request");
        sendRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                sendRequestMenu();
            }
        });
        confirmSendRequest = new JButton("Confirm");
        confirmSendRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendsInfo.addRequest(currentUsername, textInput.getText());
                clearTextField();
                previousMenu();
            }
        });
        seeRequests = new JButton("See Requests");
        seeRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "LoggedIn";
                seeRequestsMenu();
            }
        });
        viewAllRequests = new JButton("View All Requests");
        viewAllRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRequests();
            }
        });
        tagRequest = new JButton("Tag Request");
        tagRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu = "SeeRequests";
                tagRequest();
            }
        });
        //TODO: add error messages for these
        addressed = new JButton("Addressed");
        addressed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = tryParse(textInput.getText());
                sendsInfo.markAddressed(input);
            }
        });
        pending = new JButton("Pending");
        pending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = tryParse(textInput.getText());
                sendsInfo.markPending(input);
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
        date = new JTextField(10);
        startTime = new JTextField(10);
        endTime = new JTextField(10);
        speakerUsernameOne = new JTextField(10);
        speakerUsernameMulti = new JTextField(10);
        VIP = new JTextField(10);
        dateDisplay = new JLabel("Date('YYYYMMDD'):");
        startTimeDisplay = new JLabel("Start Time-24Hours('HH:MM:SS'):");
        endTimeDisplay = new JLabel("End Time-24Hours('HH:MM:SS'):");
        speakerUsernameDisplayOne = new JLabel("Enter Speaker Username:");
        speakerUsernameDisplayMulti = new JLabel("Enter Speaker Usernames ~Separate by commas:");
        VIPDisplay = new JLabel("Vip: (yes or no)");
        eventCapacity = new JTextField(12);
        successText = new JLabel();
        whichEvent = new JTextField(12);
    }

    /**
     * Setter that sets the current instance of Viewable to another
     * @param sendsInfo the instance of Viewable to set
     */
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
            case "CreateAttendeeMain":
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
                break;
            case "ChooseEvent":
                addEvent();
                break;
            case "AddRoom":
                addRoom();
                break;
            case "SeeRequests":
                seeRequestsMenu();
                previousMenu = "LoggedIn";
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
            case "CreateEvent":
                addEvent();
                break;
            case "CancelEvent":
                cancelEvent();
                break;
            case "CreateAttendeeMain":
                createAttendeeAccountMainMenu();
                break;
            case "changeEventCapacity":
                schedulingMenu();
                break;
        }
    }

    /**
     * Brings the user to the correct options screen depending on the type of user (organizer, attendee, etc.)
     */
    public void loginType() {
        boolean verifyVIP = false;
        if (sendsInfo.userIsVIP(currentUsername)) {
            changeTheme("vip");
            verifyVIP = true;
        }
        switch (loginType) {
            case "Attendee":
                if (verifyVIP){
                    frame.setTitle("VIP Attendee Account");
                } else {
                    frame.setTitle("Attendee Account");
                }
                loggedInAttendee();
                break;
            case "Organizer":
                if (verifyVIP){
                    frame.setTitle("VIP Organizer Account");
                } else {
                    frame.setTitle("Organizer Account");
                }
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

    /**
     * Returns to the main options menu of a logged in user from the message or sign up menus
     * @param type
     */
    public void backToMain(String type) {
        if (type.equals("Msg")){
            frame.remove(messagingDashboard);
        }else if(type.equals("SignUp")){
            frame.remove(signUpDashboard);
        }
        frame.add(buttonPanel);
        refresh();
        loginType();
    }

    /**
     * Sets the size of the JComponents and ensures they display properly
     */
    public void refresh() {
        frame.pack();
        frame.repaint();
    }

    private void createThemes() {
        try {
            regularTheme = new SynthLookAndFeel();
            regularTheme.load(Dashboard.class.getResourceAsStream("sadness.xml"), Dashboard.class);
            vipTheme = new SynthLookAndFeel();
            vipTheme.load(Dashboard.class.getResourceAsStream("vip.xml"), Dashboard.class);
        } catch (Exception e) {
            failedMenu("Failed to create themes. Check if you're missing XML files.");
        }
    }

    /**
     * Changes the theme of the GUI depending on if the user is a VIP or not
     * @param themeName
     */
    public void changeTheme(String themeName) {
        try {
            switch (themeName) {
                case "regular":
                    UIManager.setLookAndFeel(regularTheme);
                    SwingUtilities.updateComponentTreeUI(frame);
                    break;
                case "vip":
                    UIManager.setLookAndFeel(vipTheme);
                    SwingUtilities.updateComponentTreeUI(frame);
                    break;
            }
            frame.pack();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        int result = fileChooser.showSaveDialog(buttonPanel);
        if (result == JFileChooser.APPROVE_OPTION) {
            sendsInfo.saveProgram(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void loadFile() {
        currentMenu = "LoadingConference";
        int result = fileChooser.showOpenDialog(buttonPanel);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (sendsInfo.loadConferenceButton(fileChooser.getSelectedFile().getAbsolutePath())) {
                loginSignup();
            } else {
                failedMenu("Load failed.");
            }
        }
    }
}