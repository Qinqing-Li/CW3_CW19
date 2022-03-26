import command.*;
import controller.Controller;
import logging.Logger;
import model.*;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

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

    private static void registerEntertainmentProvider1(Controller controller) {

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

    private static void registerEntertainmentProvider2(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Cinema Conglomerate",
                "Global Office, International Space Station",
                "$$$@there'sNoEmailValidation.wahey!",
                "Mrs Representative",
                "odeon@cineworld.com",
                "F!ghT th3 R@Pture",
                List.of("Dr Strangelove"),
                List.of("we_dont_get_involved@cineworld.com")
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
