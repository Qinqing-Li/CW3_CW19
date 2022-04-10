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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookEventSystemTest {

    private static Logger logger;
    private LogStatus status;
    private static Controller controller;
    private static BookEventCommand bookTicketedEventCommand;
    private static BookEventCommand bookNonTicketedEventCommand;
    private static CreateTicketedEventCommand createTicketedEvent;
    private static CancelEventCommand cancelEventCommand;
    private static CreateNonTicketedEventCommand createNonTicketedEventCommand;
    private static GetAvailablePerformanceTicketsCommand GetAvailablePerformanceTicketsCommand;
    private static RegisterEntertainmentProviderCommand registerEntertainmentProviderCommand;
    private static LoginCommand loginEntertainmentProviderCommand;
    private static AddEventPerformanceCommand addEventNonTicketedPerformanceCommand;
    private static AddEventPerformanceCommand addEventTicketedPerformanceCommand;
    private static AddEventPerformanceCommand addEndedPerformance;
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
    private final static LocalDateTime sucstartDateTime = LocalDateTime.now().plusMinutes(10);
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

    private static long eventNumberNonTicketed;
    private static long eventNumberTicketed;
    private static long performanceNumberEnded;

    private static long performanceNumberNonTicketed;
    private static long performanceNumberTicketed;




    // performance with performanceNumber 1 is valid, 2 is cancelled, 99999 is not valid, 3 is ongoing performance number
    private final static long testPerformanceNumber = 1;
    private final static long test5PerformanceNumber = 2;
    private final static long test9PerformanceNumber = 99999;
    private final static long successPerformanceNumber = 3;

    @BeforeAll
    public static void registeringConsumer(){
        controller = new Controller();

        RegisterConsumerCommand registerCmd = new RegisterConsumerCommand ("abc",
                TESTEMAIL,
                "07497111111",
                TESTPASSWORD,
                TESTEMAIL);
        controller.runCommand(registerCmd);


        //controller.runCommand(new LogoutCommand());

        //first register a EP
        registerEntertainmentProviderCommand = new RegisterEntertainmentProviderCommand(
                orgName,
                orgAddress,
                paymentAccountEmail,
                mainRepName,
                mainRepEmail,
                password,
                otherRepNames,
                otherRepEmails
        );
        controller.runCommand(registerEntertainmentProviderCommand);
        //controller.runCommand(registerCmd);

        loginEntertainmentProviderCommand = new LoginCommand(mainRepEmail, password);

        //EP create a non-ticketed event --- for test4
        createNonTicketedEventCommand = new CreateNonTicketedEventCommand(title, type);
        controller.runCommand(createNonTicketedEventCommand);
        eventNumberNonTicketed = createNonTicketedEventCommand.getResult();

        //EP add an event performance --- for test4
        addEventNonTicketedPerformanceCommand = new AddEventPerformanceCommand(
                eventNumberNonTicketed,
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
        controller.runCommand(addEventNonTicketedPerformanceCommand);
        performanceNumberNonTicketed = addEventNonTicketedPerformanceCommand.getResult().getPerformanceNumber();

        createTicketedEvent = new CreateTicketedEventCommand("Ticketed",
                EventType.Dance,
                300,
                20,
                false);
        controller.runCommand(createTicketedEvent);
        eventNumberTicketed = createTicketedEvent.getResult();

        //EP add an event performance --- for success case
        addEventTicketedPerformanceCommand = new AddEventPerformanceCommand(
                eventNumberTicketed,
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
        controller.runCommand(addEventTicketedPerformanceCommand);
        performanceNumberTicketed = addEventTicketedPerformanceCommand.getResult().getPerformanceNumber();

        bookTicketedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                performanceNumberTicketed,
                1
        );

        bookNonTicketedEventCommand = new BookEventCommand(
                eventNumberNonTicketed,
                performanceNumberNonTicketed,
                1
        );


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

        controller.runCommand(loginEntertainmentProviderCommand);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
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
        bookTicketedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                testPerformanceNumber,
                1
        );
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
        });
        assertEquals(
                "Logged in user must be a consumer",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );
    }


    @Test
    @DisplayName("testReturnError3, Error: event with given number does not exist")
    public void testReturnError3(){
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_FOUND;
        bookTicketedEventCommand = new BookEventCommand(
                504,
                testPerformanceNumber,
                1);
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
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
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookNonTicketedEventCommand);
        });
        assertEquals("Event must be a ticketed event.", expectedError.getMessage(),
                "Assertion error message should be the same");

    }


    @Test
    @DisplayName("testReturnError5, Error: this event has been cancelled and can no longer be booked")
    public void testReturnError5(){
        this.status = LogStatus.BOOK_EVENT_EVENT_NOT_ACTIVE;

        controller.runCommand(new LogoutCommand());
        controller.runCommand(loginEntertainmentProviderCommand);

        //an event is cancelled by provider
        cancelEventCommand = new CancelEventCommand(
                eventNumberTicketed,
                "event cancelled"
        );
        controller.runCommand(cancelEventCommand);

        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand(TESTEMAIL, TESTPASSWORD));

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
        });

        assertEquals(
                "Event number must correspond to an existing event.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );


        controller.runCommand(new LogoutCommand());
        controller.runCommand(loginEntertainmentProviderCommand);

        // recreate event and performance

        createTicketedEvent = new CreateTicketedEventCommand("Ticketed2",
                EventType.Dance,
                300,
                20,
                false);
        controller.runCommand(createTicketedEvent);
        eventNumberTicketed = createTicketedEvent.getResult();

        addEventTicketedPerformanceCommand = new AddEventPerformanceCommand(
                eventNumberTicketed,
                sucvenueAddress,
                LocalDateTime.now().plusMinutes(11),
                sucendDateTime,
                sucperformerNames,
                suchasSocialDistancing,
                suchasAirFiltration,
                sucisOutdoors,
                succapacityLimit,
                sucvenueSize
        );
        controller.runCommand(addEventTicketedPerformanceCommand);
        performanceNumberTicketed = addEventTicketedPerformanceCommand.getResult().getPerformanceNumber();


        controller.runCommand(createTicketedEvent);
        controller.runCommand(addEventTicketedPerformanceCommand);
        controller.runCommand(new LogoutCommand());
    }


    @Test
    @DisplayName("testReturnError6, Error: invalid number of tickets provided")
    public void testReturnError6(){
        this.status = LogStatus.BOOK_EVENT_INVALID_NUM_TICKETS;

        bookTicketedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                performanceNumberTicketed,
                99999
        );

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
        });
        assertEquals(
                "Not enough tickets available.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );

        bookTicketedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                testPerformanceNumber,
                -99999
        );

        expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
        });
        assertEquals(
                "Number of tickets must be at least 1.",
                expectedError.getMessage(),
                "Assertion error message should be the same"
        );
    }


    @Test
    @DisplayName("testReturnError7, Error: performance has already ended")
    public void testReturnError7(){
        this.status = LogStatus.BOOK_EVENT_ALREADY_OVER;

        controller.runCommand(new LogoutCommand());
        controller.runCommand(loginEntertainmentProviderCommand);
        addEndedPerformance = new AddEventPerformanceCommand(
                eventNumberTicketed,
                sucvenueAddress,
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().minusMinutes(9),
                sucperformerNames,
                suchasSocialDistancing,
                suchasAirFiltration,
                sucisOutdoors,
                succapacityLimit,
                sucvenueSize);
        controller.runCommand(addEndedPerformance);
        performanceNumberEnded = addEndedPerformance.getResult().getPerformanceNumber();
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand(TESTEMAIL, TESTPASSWORD));

        BookEventCommand bookEndedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                performanceNumberEnded,
                1
        );

        if(now.isAfter(endDateTime)){
            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                controller.runCommand(bookEndedEventCommand);
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
    @DisplayName("testReturnError8, Error: Non-existing performance test")
    public void testReturnError8(){
        this.status = LogStatus.BOOK_EVENT_PERFORMANCE_NOT_FOUND;
        bookTicketedEventCommand = new BookEventCommand(
                eventNumberTicketed,
                42,
                1
        );
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(bookTicketedEventCommand);
        });
        assertEquals(
                "Performance number must correspond to an existing performance of the event.",
                expectedError.getMessage(),
                "Message should indicate that it's a non-existing performance"
        );
    }

    @Test
    @DisplayName("testSuccessCase, booking was made")
    public void testSuccessCase(){

        controller.runCommand(bookTicketedEventCommand);

        GetAvailablePerformanceTicketsCommand = new GetAvailablePerformanceTicketsCommand(
                eventNumberTicketed,
                performanceNumberTicketed
        );
        controller.runCommand(GetAvailablePerformanceTicketsCommand);
        this.status = LogStatus.BOOK_EVENT_SUCCESS;

        assertEquals(
                299,
                GetAvailablePerformanceTicketsCommand.getResult(),
                "Result should be num.of tickets: 2, but is: " + GetAvailablePerformanceTicketsCommand.getResult());
    }

}