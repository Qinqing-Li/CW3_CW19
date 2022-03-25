import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import state.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestBookingRecordsST {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void governmentAcceptAllSponsorships(Controller controller) {
        ListSponsorshipRequestsCommand cmd = new ListSponsorshipRequestsCommand(true);
        controller.runCommand(cmd);
        List<long> requestNumbers = cmd.getRequestNumber();

        for (long requestNumber : requestNumbers) {
            controller.runCommand(new RespondSponsorshipCommand(
                    requestNumber.getRequestNumber(), 25
            ));
        }
    }

    loginGovernmentRepresentative(controller);
    governmentAcceptAllSponsorships(controller);
    controller.runCommand(new LogoutCommand());

}
