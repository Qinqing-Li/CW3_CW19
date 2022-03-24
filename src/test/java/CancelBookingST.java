import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import external.*;
import state.BookingState;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelBookingST {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void providerCancelFirstEvent(Controller controller) {
        ListEventsCommand cmd = new ListEventsCommand(true, true);
        controller.runCommand(cmd);
        List<Event> events = cmd.getResult();
        controller.runCommand(new CancelEventCommand(events.get(0).getEventNumber(), "Trololol"));
    }

    loginEntertainmentProvider(controller);
    providerCancelFirstEvent(controller);
    controller.runCommand(new LogoutCommand());
}
