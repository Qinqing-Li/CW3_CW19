import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookEventSystemTest {


    private static Logger logger;
    private LogStatus status;
    private Controller controller;
    private static BookEventCommand BookEventCommand;
    private static CancelEventCommand CancelEventCommand;
    private static CreateNonTicketedEventCommand CreateNonTicketedEventCommand;
    private static GetAvailablePerformanceTicketsCommand GetAvailablePerformanceTicketsCommand;
    private static RegisterEntertainmentProviderCommand RegisterEntertainmentProviderCommand;
    private static AddEventPerformanceCommand AddEventPerformanceCommand;
    private static Context context;

    //for test5
    private final static BookingStatus bookingstatus = BookingStatus.PaymentFailed;

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

    //mock event performance setup, past event
    //eventNumber : use private final static long testEventNumber = 1;
    private final static String venueAddress = "abc";
    private final static LocalDateTime startDateTime = LocalDateTime.now().minusYears(1);
    private final static LocalDateTime endDateTime = LocalDateTime.now().minusMinutes(3);
    private final static List<String> performerNames = Arrays.asList("I", "C");
    private final static boolean hasSocialDistancing = false;
    private final static boolean hasAirFiltration = false;
    private final static boolean isOutdoors = false;
    private final static int capacityLimit = 30;
    private final static int venueSize = 50;
    LocalDateTime now = LocalDateTime.now();

    //mock event performance setup, ongoing event for success case
    //eventNumber : use private final static long successEventNumber = 4;
    private final static String sucvenueAddress = "abc";
    private final static LocalDateTime sucstartDateTime = LocalDateTime.now();
    private final static LocalDateTime sucendDateTime = LocalDateTime.now().plusYears(1);
    private final static List<String> sucperformerNames = Arrays.asList("I", "C");
    private final static boolean suchasSocialDistancing = false;
    private final static boolean suchasAirFiltration = false;
    private final static boolean sucisOutdoors = false;
    private final static int succapacityLimit = 30;
    private final static int sucvenueSize = 50;

    //mock non-ticketed event data
    private final static String title = "free event";
    private final static EventType type = EventType.Music;

    // event with eventNumber 1 is passed ticketed event, 2 is non-ticketed, 3 is cancelled, 4 is ongoing ticketed event
    private final static long testEventNumber = 1;
    private final static long test3EventNumber = 99999;
    private final static long test4EventNumber = 2;
    private final static long test5EventNumber = 3;
    private final static long successEventNumber = 4;

    // performance with performanceNumber 1 is valid, 2 is cancelled, 99999 is not valid, 3 is ongoing performance number
    private final static long testPerformanceNumber = 1;
    private final static long test5PerformanceNumber = 2;
    private final static long test9PerformanceNumber = 99999;
    private final static long successPerformanceNumber = 3;

    @BeforeAll
    public void registeringConsumer(){
        controller = new Controller();

        RegisterConsumerCommand registerCmd = new RegisterConsumerCommand ("abc",
                TESTEMAIL,
                "07497111111",
                TESTPASSWORD,
                TESTEMAIL);

        controller.runCommand(registerCmd);

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
        this.status = LogStatus.BOOK_EVENT_USER_NOT_CONSUMER;

        //first register a EP
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                orgName,
                orgAddress,
                paymentAccountEmail,
                mainRepName,
                mainRepEmail,
                password,
                otherRepNames,
                otherRepEmails
        ));

        //then EP login
        controller.runCommand(new LoginCommand(mainRepEmail,password));

        //EP create a non-ticketed event --- for test4
        //TODO how to associate event number with test4EventNumber?
        CreateNonTicketedEventCommand = new CreateNonTicketedEventCommand(title, type);
        BookEventCommand = new BookEventCommand(
                testEventNumber,
                testPerformanceNumber,
                1
        );

        //EP add an event performance --- for test8
        AddEventPerformanceCommand = new AddEventPerformanceCommand(
                testEventNumber,
                venueAddress,
                startDateTime,
                endDateTime,
                performerNames,
                hasSocialDistancing,
                hasAirFiltration,
                isOutdoors,
                capacityLimit,
                venueSize
        );

        //EP add an event performace --- for success case
        AddEventPerformanceCommand = new AddEventPerformanceCommand(
                successEventNumber,
                sucvenueAddress,
                sucstartDateTime,
                sucendDateTime,
                sucperformerNames,
                suchasSocialDistancing,
                suchasAirFiltration,
                sucisOutdoors,
                succapacityLimit,
                sucvenueSize
        );

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals(
                "Logged in user must be a consumer",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );
    }


    @Test
    @DisplayName("testReturnError2, Error: consumer is not logged in")
    @Tag("skipBeforeEach")
    //skip BeforeEach to avoid login, in order to test situation when consumer is not logged in.
    public void testReturnError2(){
        BookEventCommand = new BookEventCommand(
                testEventNumber,
                testPerformanceNumber,
                1
        );
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals(
                "Consumer is not logged in.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );
    }


    @Test
    @DisplayName("testReturnError3, Error: event with given number does not exist")
    public void testReturnError3(){
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_FOUND;
        BookEventCommand = new BookEventCommand(
                test3EventNumber,
                testPerformanceNumber,
                1);
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
        //event.getEventNumber() == eventNumber && event instanceof TicketedEvent
        //associate test4EventNumber with non-ticketed event, assume for now
        BookEventCommand = new BookEventCommand(
                test4EventNumber,
                testPerformanceNumber,
                1
        );
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals("Event must be a ticketed event.", expectedError.getMessage(),
                "Assertion error message should be the same");

    }


    @Test
    @DisplayName("testReturnError5, Error: this event has been cancelled and can no longer be booked")
    public void testReturnError5(){
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_ACTIVE;

        //an event is cancelled by provider
        CancelEventCommand = new CancelEventCommand(
                test5EventNumber,
                "event cancelled"
        );
        //customer try to make a new booking
        BookEventCommand = new BookEventCommand(
                test5EventNumber,
                test5PerformanceNumber,
                1);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });

        assertEquals(
                "Event number must correspond to an existing event.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );

    }


    @Test
    @DisplayName("testReturnError6, Error: invalid number of tickers provided")
    public void testReturnError6(){
        this.status = LogStatus.BOOK_EVENT_INVALID_NUM_TICKETS;

        BookEventCommand = new BookEventCommand(
                testEventNumber,
                testPerformanceNumber,
                99999
        );

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals(
                "Not enough tickets available.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );

        BookEventCommand = new BookEventCommand(
                testEventNumber,
                testPerformanceNumber,
                -99999
        );

        assertEquals(
                "Number of tickets must be at least 1.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );
    }


    @Test
    @DisplayName("testReturnError7, Error: the payment is unsuccessful")
    public void testReturnError7(){
        this.status = LogStatus.BOOK_EVENT_PAYMENT_FAILED;
        if(bookingstatus == BookingStatus.PaymentFailed){
            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                BookEventCommand.execute(context);
            });
            assertEquals(
                    "Transaction unsuccessful.",
                    expectedError.getMessage(),
                    "Message should indicate transaction unsuccessful"
            );
        }
    }


    @Test
    @DisplayName("testReturnError8, Error: performance has already ended")
    public void testReturnError8(){
        this.status = LogStatus.BOOK_EVENT_ALREADY_OVER;
        BookEventCommand = new BookEventCommand(
                testEventNumber,
                testPerformanceNumber,
                1
        );
        if(now.isAfter(endDateTime)){
            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                BookEventCommand.execute(context);
            });
            assertEquals(
                    "Selected performance has already ended.",
                    expectedError.getMessage(),
                    "Message should indicate transaction unsuccessful"
            );
        }else{
            System.out.println("inspect test8");
        }
    }


    @Test
    @DisplayName("testReturnError9, Error: performance has already ended")
    public void testReturnError9(){
        this.status = LogStatus.BOOK_EVENT_PERFORMANCE_NOT_FOUND;
        BookEventCommand = new BookEventCommand(
                testEventNumber,
                test9PerformanceNumber,
                1
        );
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            BookEventCommand.execute(context);
        });
        assertEquals(
                "Performance number must correspond to an existing performance of the event.",
                expectedError.getMessage(),
                "Message should indicate transaction unsuccessful"
        );
    }

    @Test
    @DisplayName("testSuccessCase, booking was made")
    public void testSuccessCase(){
        BookEventCommand = new BookEventCommand(
                successEventNumber,
                successPerformanceNumber,
                2
        );
        GetAvailablePerformanceTicketsCommand = new GetAvailablePerformanceTicketsCommand(
                successEventNumber,
                successPerformanceNumber
        );
        this.status = LogStatus.BOOK_EVENT_SUCCESS;

        assertEquals(
                2,
                GetAvailablePerformanceTicketsCommand.getResult(),
                "Result should be num.of tickets: 2");
    }

}
