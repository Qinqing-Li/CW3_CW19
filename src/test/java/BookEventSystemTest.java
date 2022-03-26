import command.*;
import controller.Controller;
import logging.Logger;
import model.*;

import java.util.List;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;


public class BookEventSystemTest {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void consumerBookNthTicketedEvent(Controller controller, int n) {
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> events = cmd.getResult();

        for (Event event : events) {
            if (event instanceof TicketedEvent) {
                n--;
            }
            if (n <= 0) {
                //Collection<EventPerformance> performance = event.getEventPerformance();
                Collection<EventPerformance> performance = Booking.getEventPerformance();
                controller.runCommand(new BookEventCommand(
                        //event.getBookingNumber(),
                        Booking.getBookingNumber(),
                        performance.iterator().next().getEventPerformance(),
                        1
                ));
                return;
            }
        }
    }


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

}
