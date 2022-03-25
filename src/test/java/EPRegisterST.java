import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import external.MockEntertainmentProviderSystem;
import state.UserState;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EPRegisterST {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }
// To register provide the following information: name, organisation name, organisation main address, email
// and password, phone number, other representative name(s) and their email addresses, a username or email
//address for a company account on the payment system
    private static void register1EntertainmentProvider(Controller controller) {

        controller.runCommand(new RegisterEntertainmentProviderCommand(

                "No org",
                "Leith Walk",
                "busk@every.day",
                "Stephen King",
                "busk@every.day",
                Collections.emptyList(),
                Collections.emptyList()
        ));

    }

    @Test
    void getRegisteredConsumers(){

        GovernmentReport1Command cmd = new GovernmentReport1Command("Entertainment Providers");
        controller.runCommand(cmd);
        List<Consumer> consumers = cmd.getResult();

        assertEquals(2, consumers.size());
        assertTrue(EPs.stream().anyMatch(EP -> this.orgName.equals("No org")));
        assertTrue(EPs.stream().anyMatch(EP -> this.mainRepName.equals("Stephen King")));

    }
}
