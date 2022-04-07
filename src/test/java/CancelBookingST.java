import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;

import static model.BookingStatus.Active;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    //event inputs for CancelEventCommand
    private final static long bookingNumber = 1;
    private final static long test3bookingNumber = 99999;
    //event with booking number 2 is no longer active
    private final static long test4bookingNumber = 2;

    @BeforeAll
    public void registeringConsumer(){
        controller = new Controller();
        controller.runCommand(new RegisterConsumerCommand ("abc",
                TESTEMAIL,
                "07497111111",
                TESTPASSWORD,
                TESTEMAIL)
        );

        return controller.runCommand(new RegisterConsumerCommand.getResult());
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
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
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
        assertEquals("Consumer is not logged in.", expectedError.getMessage(), "Assertion error message should be the same");
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
            Booking currentBooking = context.getBookingState().findBookingByNumber(bookingNumber);
            assert currentBooking.getStatus() != Active;
        });
        assertEquals("Booking must be active.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError5, Error: the booking cannot be cancelled within 24 hours of the performance")
    public void testReturnError5(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber));
            Booking currentBooking = context.getBookingState().findBookingByNumber(bookingNumber);
            assert currentBooking.getEventPerformance().getStartDateTime().minusDays(1).isAfter(LocalDateTime.now());
        });
        assertEquals("The booked performance start must be at least 24hrs away from now.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    @DisplayName("testReturnError6, Error: the refund is unsuccessful")
    public void testReturnError6(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new CancelBookingCommand(bookingNumber));
            assert context.getPaymentSystem().processRefund(null,null,0);
        });
        assertEquals("The booked performance start must be at least 24hrs away from now.", expectedError.getMessage(), "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testSuccessCase, booking was made")
    public void testSuccessCase(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
        controller.runCommand(new CancelBookingCommand(bookingNumber));
        });
        assertEquals(true, expectedError.getMessage(), "Result should be: true");
    }
}
