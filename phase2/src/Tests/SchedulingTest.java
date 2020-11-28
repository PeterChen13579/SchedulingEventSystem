package Tests;

import java.time.LocalDateTime;

import Controllers.SchedulingSystem;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.util.List;

public class SchedulingTest {
    public static void main(String[] args){
//        parseStringToLocalDateTimeTest();
//        parseStringToDateAndTimeTest();
//        isTimeAvailableTest();
//        controllerTest();
//        eventPresenterTest();
//        getAllAttendeesTest();
        doTimesOverlapTest();


    }
    public static void doTimesOverlapTest(){
        EventManager em = new EventManager();
        List<LocalDateTime> time = em.parseStringToLocalDateTime("20201109", "09:07:00", "10:28:00");
        List<LocalDateTime> time2 = em.parseStringToLocalDateTime("20201109", "10:28:00", "11:28:00");
        System.out.println(em.doTimesOverlap(time.get(0), time.get(1), time2.get(0), time2.get(1)));

    }
//    public static boolean test(){
//        EventManager em = new EventManager();
//        List<LocalDateTime> time = em.parseStringToLocalDateTime("20201109", "09:00:00", "10:00:00");
//        List<LocalDateTime> time2 = em.parseStringToLocalDateTime("20201109", "10:00:00", "11:00:00");
//        return time.get(1).isBefore(time2.get(0));
//    }

//    public static void parseStringToLocalDateTimeTest(){
//        EventManager em = new EventManager();
//        List<LocalDateTime> time = em.parseStringToLocalDateTime("20201109", "09:04:20");
//        System.out.println(time);
//    }
//
//    public static void parseStringToDateAndTimeTest(){
//        EventManager em = new EventManager();
//        System.out.println(em.parseStringToLocalDate("20190332"));
//        System.out.println(em.parseStringToLocalTime("09:06:60"));
//    }
//
//    public static void isTimeAvailableTest(){
//        EventManager em = new EventManager();
//        System.out.println(em.isTimeAvailable("16:00:00"));
//    }
//
//    public static void controllerTest(){
//        RoomManager rm = new RoomManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager();
//        SchedulingSystem s = new SchedulingSystem(em, rm, um);
//
//        rm.createRoom("216");
//        um.createSpeakerAccount("Tom", "12345");
//
////        s.addEvent("20201130", "16:00:00", "216", "Tom", "Intro 101");
//        s.run();
//    }
//
//    public static void eventPresenterTest(){
//        RoomManager rm = new RoomManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager();
//        SchedulingSystem s = new SchedulingSystem(em, rm, um);
//
//        rm.createRoom("216");
//        um.createSpeakerAccount("Tom", "12345");
//
////        s.addEvent("20201130", "16:00:00", "216", "Tom", "Intro 102");
////        s.addEvent("20201130", "09:00:00", "216", "Tom", "Intro 101");
//
//        EventPresenter ep = new EventPresenter(em,um);
//        ep.displayEvents();
//    }

//    public static void getAllAttendeesTest(){
//        RoomManager rm = new RoomManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager();
//        SchedulingSystem s = new SchedulingSystem(em, rm, um);
//
//
//        rm.createRoom("216");
//        um.createSpeakerAccount("Tom", "12345");
//
////        s.addEvent("20201130", "16:00:00", "216", "Tom", "Intro 102");
////        s.addEvent("20201130", "09:00:00", "216", "Tom", "Intro 101");
//
//        em.addAttendee("Bob","Intro 101");
//        em.addAttendee("Lisa","Intro 101");
//        System.out.println(em.getAllAttendeesByTitle("Intro 101"));
//
//    }

}
