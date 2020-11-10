package Tests;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Controllers.SchedulingSystem;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SchedulingTest {
    public static void main(String[] args){
//        parseStringToLocalDateTimeTest();
//        parseStringToDateAndTimeTest();
//        isTimeAvailableTest();
        controllerTest();

    }

    public static void parseStringToLocalDateTimeTest(){
        EventManager em = new EventManager();
        List<LocalDateTime> time = em.parseStringToLocalDateTime("20201109", "09:04:20");
        System.out.println(time);
    }

    public static void parseStringToDateAndTimeTest(){
        EventManager em = new EventManager();
        System.out.println(em.parseStringToLocalDate("20190332"));
        System.out.println(em.parseStringToLocalTime("09:06:60"));
    }

    public static void isTimeAvailableTest(){
        EventManager em = new EventManager();
        System.out.println(em.isTimeAvailable("16:00:00"));
    }

    public static void controllerTest(){
        SchedulingSystem s = new SchedulingSystem();
        RoomManager rm = new RoomManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager();

        s.setEventManager(em);
        s.setRoomManager(rm);
        s.setUserManager(um);

        rm.createRoom("216");
        um.createSpeakerAccount("Tom", "12345");

        s.addEvent("20201130", "16:00:00", "216", "Tom", "Intro 101");
        s.run();
    }

}
