import command.*;
import controller.Controller;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;


public class CreateEventST {

    private static Controller controller;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void createEventNotLoggedIn() {

        // try to create a non-ticketed event
        try {
            CreateNonTicketedEventCommand eventCmd = new CreateNonTicketedEventCommand(
                    "Music for everyone!",
                    EventType.Music
            );
            controller.runCommand(eventCmd);
            assertNull(eventCmd.getResult(),
                    "Command should not have created a non-ticketed event as a user is not logged in," +
                            " but one was created.");
        } catch(Exception e) {
            return;
        }

    }

    @Test
    void createEventLoggedInAsConsumer() {
        controller.runCommand(new RegisterConsumerCommand("Bob",
                "bob@gmail.com",
                "01234 567890",
                "bobisawesome",
                "bobpaypal@gmail.com"));
        controller.runCommand(new LoginCommand("bob@gmail.com", "bobisawesome"));

        // try to create a non-ticketed event
        try {
            CreateNonTicketedEventCommand eventCmd = new CreateNonTicketedEventCommand(
                    "Music for everyone!",
                    EventType.Music
            );
            controller.runCommand(eventCmd);
            assertNull(eventCmd.getResult(),
                    "Command should not have created a non-ticketed event as a consumer is logged in," +
                            " but one was created.");
        } catch(Exception e) {
            return;
        }
    }


    @Test
    void createEventAsEntertainmentProvider() {

        // login entertainment provider
        controller.runCommand(new RegisterEntertainmentProviderCommand("Dance club",
                "danceclub@gmail.com",
                "dancepaypal@gmail.com",
                "Sir Bruce Forsyth",
                "bruce@gmail.com",
                "seven777",
                Collections.emptyList(),
                Collections.emptyList()));
        controller.runCommand(new LoginCommand("danceclub@gmail.com", "seven777"));

        // create non-ticketed event
        try {
            CreateNonTicketedEventCommand eventCmd = new CreateNonTicketedEventCommand(
                    "Music for everyone!",
                    EventType.Music
            );
            controller.runCommand(eventCmd);
            assertNotNull(eventCmd.getResult(),
                    "Command should have created a non-ticketed event but null was returned.");
        } catch(Exception e) {
            return;
        }

        // create ticketed event
        try {
            CreateTicketedEventCommand eventCmd2 = new CreateTicketedEventCommand(
                    "Music for everyone 2!",
                    EventType.Music,
                    500,
                    20,
                    false
            );
            controller.runCommand(eventCmd2);

            // test if successful
            assertNotNull(eventCmd2.getResult(),
                    "Command should have created a ticketed event but null was returned.");
        } catch(Exception e) {
            return;
        }

        // create event of same name
        try {
            CreateNonTicketedEventCommand eventCmd3 = new CreateNonTicketedEventCommand(
                    "Music for everyone!",
                    EventType.Music
            );
            controller.runCommand(eventCmd3);
            assertNull(eventCmd3.getResult(),
                    "Command should not have created a ticketed event with the same name but it was created.");
        } catch(Exception e) {
            return;
        }

    }
}
