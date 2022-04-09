import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;

import static model.BookingStatus.Active;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CancelBookingST {

    private static Context context;
    private static Controller controller;


    //mock consumer data
    private final static String TESTEMAIL = "john@gmail.com";
    private final static String TESTPASSWORD = "john#password";

    //mock EP data
    private final static String orgName = "Umbrella Corporation";
    private final static String orgAddress = "Raccoon city";
    private final static String paymentAccountEmail = "studentsupport@eusa.ed.ac.uk";
    private final static String mainRepName = "egg";
    private final static String mainRepEmail = "egg@gmail.com";
    private final static String password = "Eggggg";
    private final static List<String> otherRepNames = Arrays.asList("India", "China");
    private final static List<String> otherRepEmails = Arrays.asList("i", "c");

    //event constants
    private long eventNumber;
    private long performanceNumber;
    private long performanceNumber2;

    //event inputs for CancelEventCommand
    private long bookingNumber;
    private long bookingNumber2;
    private final static long test3bookingNumber = 99999;
    //event with booking number 2 is no longer active
    private final static long test4bookingNumber = 2;

    @BeforeAll
    public void registeringConsumer(){
        controller = new Controller();

        // login entertainment provider
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Dance club",
                "7 Dance Road",
                "dancepaypal@gmail.com",
                "Sir Bruce Forsyth",
                "bruce@gmail.com",
                "seven777",
                Collections.emptyList(),
                Collections.emptyList()));
        controller.runCommand(new LoginCommand("bruce@gmail.com", "seven777"));

        // create event
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Music for everyone!",
                EventType.Music,
                10,
                20,
                false
        );
        controller.runCommand(eventCmd);
        eventNumber = eventCmd.getResult();

        // create performance
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "Leith as usual",
                LocalDateTime.of(2030, 3, 20, 4, 20),
                LocalDateTime.of(2030, 3, 20, 6, 45),
                List.of("The same musician"),
                true,
                true,
                true,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE
        );

        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNumber,
                "Leith as usual",
                LocalDateTime.now().plusMinutes(10),
                LocalDateTime.now().plusHours(1),
                List.of("The same musician"),
                true,
                true,
                true,
                300,
                300
        );

        controller.runCommand(performanceCmd);
        controller.runCommand(performanceCmd2);
        performanceNumber = performanceCmd.getResult().getPerformanceNumber();
        performanceNumber2 = performanceCmd2.getResult().getPerformanceNumber();

        // logout entertainment provider
        controller.runCommand(new LogoutCommand());

        RegisterConsumerCommand registerCmd = new RegisterConsumerCommand ("abc",
                TESTEMAIL,
                "07491111117",
                TESTPASSWORD,
                TESTEMAIL);

        controller.runCommand(registerCmd);

        controller.runCommand(new LoginCommand(TESTEMAIL, TESTPASSWORD));
        BookEventCommand bookCmd = new BookEventCommand(eventNumber, performanceNumber, 1);
        BookEventCommand bookCmd2 = new BookEventCommand(eventNumber, performanceNumber2, 1);

        controller.runCommand(bookCmd);
        controller.runCommand(bookCmd2);

        bookingNumber = bookCmd.getResult();
        bookingNumber2 = bookCmd2.getResult();

        controller.runCommand(new LogoutCommand());
    }

    @BeforeEach
    void loginConsumer(final TestInfo info){
        final Set<String> testTags = info.getTags();
        if(testTags.stream().anyMatch(tag->tag.equals("skipBeforeEach"))){
            return;
        }else{
            controller.runCommand(new LoginCommand(TESTEMAIL,TESTPASSWORD));
        }
        System.out.println(info.getDisplayName());
    }

    @AfterEach
    void logoutConsumer(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if(testTags.stream().anyMatch(tag->tag.equals("skipBeforeEach"))){
            return;
        }else {
            controller.runCommand(new LogoutCommand());
        }
    }

    @Test
    @DisplayName("testReturnError1, Error: this action is only available to consumer")
    @Tag("skipBeforeEach")
    public void testReturnError1(){
        controller.runCommand(new RegisterEntertainmentProviderCommand(orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password, otherRepNames, otherRepEmails));
        controller.runCommand(new LoginCommand(mainRepEmail,password));
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber));
        });
        assertEquals("User must be consumer.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError2, Error: consumer is not logged in")
    @Tag("skipBeforeEach")
    public void testReturnError2(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber));
        });
        assertEquals("User must be consumer.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError3, Error: the logged-in user is the booking owner")
    public void testReturnError3(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(test3bookingNumber));
        });
        assertEquals("Booking number must belong to logged-in consumer.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError4, Error: the booking is no longer active")
    public void testReturnError4(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber));
            controller.runCommand(new CancelBookingCommand(bookingNumber));
        });
        assertEquals("Booking must be active.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError5, Error: the booking cannot be cancelled within 24 hours of the performance")
    public void testReturnError5(){

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber2));
        });
        assertEquals("The booked performance start must be at least 24hrs away from now.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testSuccessCase, booking was made")
    public void testSuccessCase(){
        try {

            BookEventCommand bookCmd = new BookEventCommand(eventNumber, performanceNumber, 1);
            controller.runCommand(bookCmd);
            long bookingNumber3 = bookCmd.getResult();

            CancelBookingCommand cancelCmd = new CancelBookingCommand(bookingNumber3);

            assertDoesNotThrow(() -> controller.runCommand(cancelCmd),
                    "Should not throw but throws.");

            assertEquals(true, cancelCmd.getResult(), "Result should be: true");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
