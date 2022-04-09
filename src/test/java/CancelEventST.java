import command.*;
import controller.Controller;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CancelEventST {

    private static Controller controller;
    private long eventNumber;
    private long performanceNumber;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();
        // login entertainment provider
        controller.runCommand(new RegisterEntertainmentProviderCommand("Dance club",
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

        // book event & logout
        controller.runCommand(new BookEventCommand(eventNumber, performanceNumber, 3));
        controller.runCommand(new LogoutCommand());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void cancelEventProviderNotLoggedIn() {
        try {
            CancelEventCommand cancelEventCmd = new CancelEventCommand(eventNumber, "Sorry!");

            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                controller.runCommand(cancelEventCmd);
            }, "An assertion error should be raised for wrong user password");
            assertEquals("Logged in user must be an entertainment provider", expectedError.getMessage(),
                    "Assertion error message should be the same, that entertainment provider is not logged in.");

        } catch(Exception e) {
            return;
        }
    }

    @Test
    @DisplayName("Cancelling a message without providing a message from provider.")
    void cancelEventWithoutMessage() {
        try {
            CancelEventCommand cancelEventNullMsgCmd = new CancelEventCommand(eventNumber, null);
            CancelEventCommand cancelEventEmptyMsgCmd = new CancelEventCommand(eventNumber, "");


            assertThrows(AssertionError.class, () -> {
                controller.runCommand(cancelEventNullMsgCmd);
            }, "An assertion error should be raised for null message");

            assertThrows(AssertionError.class, () -> {
                controller.runCommand(cancelEventEmptyMsgCmd);
            }, "An assertion error should be raised for empty message");


        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("End of test.");

    }


    @Test
    void cancelNonExistingEvent() {
        try {
            CancelEventCommand cancelEventCmd = new CancelEventCommand(500, "Sorry!");

            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                controller.runCommand(cancelEventCmd);
            }, "An assertion error should be raised for wrong user password");
            assertEquals("Event number must correspond to an existing event.", expectedError.getMessage(),
                    "Assertion error message should be the same, that the event number does not correspond" +
                            "to an existing event.");

        } catch(Exception e) {
            return;
        }
    }

    @Test
    void cancelEventProviderLoggedIn() {
        try {
            // login event provider
            controller.runCommand(new LoginCommand("danceclub@gmail.com", "seven777"));

            CancelEventCommand cancelEventCmd = new CancelEventCommand(eventNumber, "Sorry!");
            controller.runCommand(cancelEventCmd);
            assertTrue(cancelEventCmd.getResult(),
                    "Event should have been sucessfully cancelled.");

            ListEventsCommand checkDeletedCommand = new ListEventsCommand(false, false);
            controller.runCommand(checkDeletedCommand);
            List<Event> existingEvents = checkDeletedCommand.getResult();

            assertEquals(existingEvents.get(0).getStatus(), EventStatus.CANCELLED,
                    "Created event should be cancelled but is still active.");

        } catch(Exception e) {
            return;
        }
    }

}
