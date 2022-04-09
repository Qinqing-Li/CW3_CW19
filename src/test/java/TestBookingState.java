import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import state.BookingState;
import state.EventState;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingState {

    private static BookingState bookingState;
    private static Consumer testConsumer;
    private static NonTicketedEvent testEvent;
    private static EventPerformance testPerformance;
    private static EntertainmentProvider testProvider;
    private static Booking testBooking;

    @BeforeAll
    @DisplayName("BookingState tests")
    static void createInstance() {

        bookingState = new BookingState();

        testConsumer = new Consumer("Bob",
                "bob@gmail.com",
                "01234 567890",
                "bobthebuilder",
                "bobpaypal@gmail.com");

        testProvider = new EntertainmentProvider("Dance club",
                "danceclub@gmail.com",
                "dancepaypal@gmail.com",
                "Sir Bruce Forsyth",
                "bruce@gmail.com",
                "seven777",
                Collections.emptyList(),
                Collections.emptyList());

        testEvent = new NonTicketedEvent(1, testProvider, "Dance club", EventType.Dance);
        List<String> performerNames = new ArrayList<>();
        performerNames.add("Bruce");
        testPerformance = new EventPerformance(1,
                testEvent,
                "7 Dance Street",
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(1),
                performerNames,
                false,
                true,
                false,
                300,
                300);
        testBooking = bookingState.createBooking(testConsumer,
                testPerformance,
                1,
                20);
    }

    @Test
    @DisplayName("testfindBookingByNumber, should return booking")
    void testFindBookingByNumber(){
        assertEquals(bookingState.findBookingByNumber(1), testBooking,
                "Returned booking should be the same as one created.");
    }

    @Test
    @DisplayName("testfindBookingByNumber2, non-existing booking number should return null")
    void testFindBookingByNumber2(){
        assertNull(bookingState.findBookingByNumber(2),
                "Returned booking should be the same as one created.");
    }

    @Test
    @DisplayName("testfindBookingsByEventNumber, should return list of bookings")
    void testFindBookingsByEventNumber(){
        List<Booking> bookings = bookingState.findBookingsByEventNumber(1);
        assertSame(bookings.get(1), testBooking,
                "Returned item from booking list is not the same as one created.");

    }

    @Test
    @DisplayName("testfindBookingsByEventNumber2, non-existing event number should return empty list")
    void testFindBookingsByEventNumber2(){
        List<Booking> bookings = bookingState.findBookingsByEventNumber(2);
        List<Booking> emptyList = new ArrayList<>();
        assertEquals(bookings, emptyList,
                "Booking list should be empty but isn't.");
    }

    @Test
    @DisplayName("createBooking")
    void createBooking(){
        Booking newBooking = bookingState.createBooking(testConsumer, testPerformance, 1, 20);
        assertSame(newBooking, bookingState.findBookingByNumber(2),
                "Newly created booking should have booking number 2 and return booking but didn't.");

    }

}
