import command.*;
import controller.Context;
import controller.Controller;
import external.EntertainmentProviderSystem;
import logging.Logger;
import model.*;
import model.EventType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.*;

import static model.BookingStatus.Active;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BookEventSystemTest {


    private static Logger logger;
    private static BookEventCommand BookEventCommand;
    private static CancelEventCommand CancelEventCommand;
    private static Context context;

    private LogStatus status;
    private BookingStatus bookingstatus;
    private BookingStatus test5status = BookingStatus.CancelledByProvider;


    private static long testbookingNumber;
    private static Consumer testConsumer;
    private static Booking testBooking;
    private static EventPerformance testperformance;
    private static LocalDateTime testbookingDateTime;

    private static GovernmentRepresentative gp1;

    private final static String TESTEMAIL = "john@gmail.com";
    private final static String TESTPASSWORD = "john#password";

    private final static long testEventNumber = 1;
    private final static long test5EventNumber = 2;
    private final static long testSuccessEventNumber = 3;

    private final static long testPerformanceNumber = 1;
    private final static long test5PerformanceNumber = 2;
    private final static long testSuccessPerformanceNumber = 2;

    private final static int TestNumTicketsRequested = 2;
    private final static int testSuccessNumTicketsRequested = 1;



    @BeforeEach
    void loginConsumer(Controller controller, final TestInfo info){
        final Set<String> testTags = info.getTags();
        if(testTags.stream().anyMatch(tag->tag.equals("skipBeforeEach"))){
            return;
        }else{
            controller.runCommand(new LoginCommand(TESTEMAIL,TESTPASSWORD));
        }
    }

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void logoutConsumer(Controller controller){
        controller.runCommand(new LogoutCommand());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }



    @Test
    @DisplayName("testReturnError1, Error: this action is only available to consumer")
    public void testReturnError1(){
        this.status = LogStatus.BOOK_EVENT_USER_NOT_CONSUMER;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals("Logged in user must be a consumer", expectedError.getMessage(),
                "Assertion error message should be the same");
    }


    @Test
    @DisplayName("testReturnError2, Error: consumer is not logged in")
    @Tag("skipBeforeEach")
    //skip BeforeEach to avoid login, in order to test situation when consumer is not logged in.
    public void testReturnError2(){
        //TODO need to implement a return message e.g. "consumer is not logged in" in bookEventCommand
    }


    @Test
    @DisplayName("testReturnError3, Error: event with given number does not exist")
    public void testReturnError3(){
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_FOUND;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals("Event number must correspond to an existing event.", expectedError.getMessage(),
                "Assertion error message should be the same");

    }


    @Test
    @DisplayName("testReturnError4, Error: only ticketed event can be booked")
    public void testReturnError4(){
        this.status = LogStatus.BOOK_EVENT_NOT_A_TICKETED_EVENT;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals("Event must be a ticketed event.", expectedError.getMessage(),
                "Assertion error message should be the same");

    }


    @Test
    @DisplayName("testReturnError5, Error: this event has been cancelled and can no longer be booked")
    public void testReturnError5(){
        //an event is cancelled by provider
        CancelEventCommand = new CancelEventCommand(test5EventNumber, "event cancelled");
        //customer try to make a new booking
        BookEventCommand = new BookEventCommand(test5EventNumber, test5PerformanceNumber, 1);
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_ACTIVE;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        //TODO implement a return message "Event has been cancelled... can't be booked" in bookEventCommand
        assertEquals("Event has been cancelled and can no longer be booked.",
                expectedError.getMessage(), "Assertion error message should be the same");

    }


    @Test
    @DisplayName("testReturnError6, Error: invalid number of tickers provided")
    public void testReturnError6(){
        this.status = LogStatus.BOOK_EVENT_INVALID_NUM_TICKETS;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });

        BookEventCommand = new BookEventCommand(testEventNumber, testPerformanceNumber, TestNumTicketsRequested);
        TicketedEvent event = (TicketedEvent) context.getEventState().findEventByNumber(testEventNumber);
        EntertainmentProviderSystem providerSystem = event.getOrganiser().getProviderSystem();

        if (TestNumTicketsRequested < 1){
            assertEquals("Number of tickets must be at least 1.",
                    expectedError.getMessage(), "Assertion error message should be the same");
        }else if(TestNumTicketsRequested > providerSystem.getNumTicketsLeft(event.getEventNumber(), testPerformanceNumber)){
            assertEquals("Not enough tickets available.",
                    expectedError.getMessage(), "Assertion error message should be the same");
        }else{
            return;
        }
    }


    @Test
    @DisplayName("testReturnError7, Error: the payment is unsuccessful")
    public void testReturnError7(){
        this.status = LogStatus.BOOK_EVENT_PAYMENT_FAILED;
        if(this.bookingstatus == BookingStatus.PaymentFailed){
            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                BookEventCommand.execute(context);
            });
            assertEquals("Transaction unsuccessful.",
                    expectedError.getMessage(), "message should indicate transaction unsuccessful");
        }


    }


    @Test
    @DisplayName("testSuccessCase, booking was made")
    public void testSuccessCase(){
        //consumer make a new booking
        BookEventCommand = new BookEventCommand(testSuccessEventNumber,
                testSuccessPerformanceNumber, testSuccessNumTicketsRequested);
        this.status = LogStatus.BOOK_EVENT_SUCCESS;
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals("Transaction unsuccessful.",
                expectedError.getMessage(), "Assertion error message should be the same");

    }

}
