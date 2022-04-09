import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.*;
import state.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RequestBookingRecordsST {

    private static Controller controller;
    private long eventNumber;
    private long performanceNumber;
    private long booking1;
    private long booking2;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();
        // login entertainment provider
        controller.runCommand(new RegisterEntertainmentProviderCommand("Dance club",
                "danceclub@gmail.com",
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
                300,
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
        controller.runCommand(performanceCmd);
        performanceNumber = performanceCmd.getResult().getPerformanceNumber();

        // logout entertainment provider
        controller.runCommand(new LogoutCommand());

        // login consumer
        controller.runCommand(new RegisterConsumerCommand("Bob",
                "bob@gmail.com",
                "01234 567890",
                "bobisawesome",
                "bobpaypal@gmail.com"));
        controller.runCommand(new LoginCommand("bob@gmail.com", "bobisawesome"));

        // book 2 events, cancel 1, & logout
        BookEventCommand newBooking1 = new BookEventCommand(eventNumber, performanceNumber, 3);
        controller.runCommand(newBooking1);
        booking1 = newBooking1.getResult();

        BookEventCommand newBooking2 = new BookEventCommand(eventNumber, performanceNumber, 7);
        controller.runCommand(newBooking2);
        booking2 = newBooking2.getResult();
        controller.runCommand(new CancelBookingCommand(newBooking2.getResult()));
        controller.runCommand(new LogoutCommand());
    }

    /*
    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    } */

    @Test
    @DisplayName("Test requesting booking records when not logged in as government user.")
    void testNotGovernmentUser() {
        try {
            GovernmentReport1Command reportCmd = new GovernmentReport1Command(
                    LocalDateTime.of(2030, 3, 20, 4, 10),
                    LocalDateTime.of(2030, 3, 20, 6, 50)
            );

            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                controller.runCommand(reportCmd);
            }, "An assertion error should be raised for wrong user password");
            assertEquals("Current user must be a government representative.", expectedError.getMessage(),
                    "Assertion error message should be the same, current user is not government representative.");

        } catch(Exception e) {
            return;
        }
    }

    @Test
    @DisplayName("Test requesting booking records when logged in as government user.")
    void testGovernmentUser() {
        try {
            // login government representative
            controller.runCommand(new LoginCommand("Suzy", "wrui2fnk"));

            GovernmentReport1Command reportCmd = new GovernmentReport1Command(
                    LocalDateTime.of(2030, 3, 20, 4, 10),
                    LocalDateTime.of(2030, 3, 20, 6, 50)
            );
            controller.runCommand(reportCmd);

            assertEquals(reportCmd.getResult().size(), 2,
                    "Report should return two bookings but " + reportCmd.getResult().size() + "are given.");
            assertTrue((reportCmd.getResult().get(0).getBookingNumber() == booking1 &&
                    reportCmd.getResult().get(1).getBookingNumber() == booking2) ||
                    (reportCmd.getResult().get(0).getBookingNumber() == booking2 &&
                            reportCmd.getResult().get(1).getBookingNumber() == booking1),
                    "The bookings provided should be the same as the ones put into the system but aren't.");

        } catch(Exception e) {
            return;
        }
    }

    @Test
    @DisplayName("Test requesting booking records outside time bounds of any bookings")
    void testOutsideBounds() {
        try {
            // login government representative
            controller.runCommand(new LoginCommand("Suzy", "wrui2fnk"));

            GovernmentReport1Command reportCmd = new GovernmentReport1Command(
                    LocalDateTime.of(2010, 3, 20, 4, 10),
                    LocalDateTime.of(2010, 3, 20, 6, 50)
            );
            controller.runCommand(reportCmd);

            assertEquals(reportCmd.getResult().size(), 0,
                    "Report should return no bookings but " + reportCmd.getResult().size() + "are given.");

        } catch(Exception e) {
            return;
        }
    }

}
