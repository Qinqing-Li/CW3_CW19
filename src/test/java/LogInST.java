import command.*;
import controller.Controller;
import logging.Logger;
import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInST {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void loginGovernmentRepresentative(Controller controller) {
        controller.runCommand(new LoginCommand("margaret.thatcher@gov.uk", "The Good times  "));
    }

    private static void loginEntertainmentProvider(Controller controller) {
        controller.runCommand(new LoginCommand("anonymous@gmail.com", "anonymous"));
    }

    private static void loginConsumer1(Controller controller) {
        controller.runCommand(new LoginCommand("jbiggson1@hotmail.co.uk", "jbiggson2"));
    }

    private static void loginConsumer2(Controller controller) {
        controller.runCommand(new LoginCommand("jane@inf.ed.ac.uk", "giantsRverycool"));
    }

    private static void loginConsumer3(Controller controller) {
        controller.runCommand(new LoginCommand("i-will-kick-your@gmail.com", "it is wednesday my dudes"));
    }

    loginGovernmentRepresentative(controller);
    governmentAcceptAllSponsorships(controller);
    controller.runCommand(new LogoutCommand());

    loginEntertainmentProvider(controller);
    providerCancelFirstEvent(controller);
    controller.runCommand(new LogoutCommand());

    loginConsumer1(controller);
    consumerBookNthTicketedEvent(controller, 1);
    consumerBookNthTicketedEvent(controller, 2);
    controller.runCommand(new LogoutCommand());

    loginConsumer2(controller);
    consumerBookNthTicketedEvent(controller, 2);
    controller.runCommand(new LogoutCommand());

    loginConsumer3(controller);
    consumerBookNthTicketedEvent(controller, 4);
    controller.runCommand(new LogoutCommand());



    loginGovernmentRepresentative(controller);

}
