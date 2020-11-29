package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpDashboard extends JPanel {
    private String currentMenu, previousMenu, currentUsername, loginType;
    private JButton seeAllEvent, seeSignedEvent, back, nextPanel;
    private JButton browseEvent, signUpEvent, cancelAttendEvent;
    private JButton confirmEventSignup, confirmEventRemoval;
    private Viewable sendsInfo;
    private JTextField textInput;
    private JLabel errorText;
    private boolean running;
    private Dashboard dashboard;

    SignUpDashboard(Viewable sendsInfo, String currentUsername, String loginType, Dashboard dashboard) {
        super();
        this.setOpaque(true);
        //this.setSize(900, 600);
        currentMenu = "SignUpMenu";
        previousMenu = "LoggedIn";
        this.sendsInfo = sendsInfo;
        this.currentUsername = currentUsername;
        this.loginType = loginType;
        this.dashboard = dashboard;
        createButtons();
        running = true;
        run();
    }

    public boolean getRunning() {
        return this.running;
    }

    private void run() {
        currentMenu = "SignUpEvent";
        this.removeAll();
        this.add(browseEvent);
        this.add(signUpEvent);
        this.add(cancelAttendEvent);
        this.add(back);
        dashboard.refresh();
        
    }

    private void browseEventMenu() {
        currentMenu = "BrowseEvent";
        this.removeAll();
        this.add(seeAllEvent);
        this.add(seeSignedEvent);
        this.add(back);
        dashboard.refresh();
        
    }

    private void displayEvents(boolean allOrNot) {
        currentMenu = "DisplayEvents";
        this.removeAll();
        String[] info;
        if (allOrNot) {
            info = sendsInfo.displayAllEvents();
        } else {
            // save current username I guess to run this
            info = sendsInfo.displaySignedUpEvents(currentUsername);
        }
        if (info.length == 0) {
            errorText.setText("no events :(");
            this.add(errorText);
        } else {
            //displayList.setListData(info);
            //this.add(displayList);
        }
        this.add(back);
        dashboard.refresh();
        
    }

    private void signUpForEvent() {
        currentMenu = "SignUpForEvent";
        this.removeAll();
        this.add(textInput);
        this.add(confirmEventSignup);
        this.add(back);
        dashboard.refresh();
        
    }

    private void cancelAttendEvent() {
        currentMenu = "CancelAttendEvent";
        this.removeAll();
        this.add(textInput);
        this.add(confirmEventRemoval);
        this.add(back);
        dashboard.refresh();
        
    }

    private void createButtons() {
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
        seeAllEvent = new JButton("See All Events");
        seeAllEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked see all");
                previousMenu = "BrowseMenu";
                displayEvents(true);
            }
        });
        seeSignedEvent = new JButton("See Signed Up Events");
        seeSignedEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked see signed");
                previousMenu = "BrowseMenu";
                displayEvents(false);
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
        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousMenu();
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
        nextPanel = new JButton("Next");
        nextPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToSameMenu();
            }
        });
        textInput = new JTextField(12);
        errorText = new JLabel();
    }

    private void previousMenu() {
        System.out.println(previousMenu);
        switch (previousMenu) {
            case "LoggedIn":
                //return to dashboard
                dashboard.backToMain();
                break;
            case "BrowseMenu":
                browseEventMenu();
                previousMenu = "SignUpMenu";
                break;
            case "SignUpMenu":
                previousMenu = "LoggedIn";
                run();
                break;
        }
    }

    private void returnToSameMenu() {
        switch (currentMenu) {
            case "SignUpForEvent":
                signUpForEvent();
                break;
            case "CancelAttendEvent":
                cancelAttendEvent();
                break;
        }
    }

    private void failedMenu(String failedMessage) {
        this.removeAll();
        errorText.setText(failedMessage);
        this.add(errorText);
        this.add(nextPanel);
        dashboard.refresh();
    }

    private void clearTextField() {
        textInput.setText("");
    }

}
