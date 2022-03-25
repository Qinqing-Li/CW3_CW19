import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import state.UserState;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsumerRegisterST {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

// Create 3 realistic inputs for consumer register
// Method name inherit from GevReport example, may need change
// Assume boolean and int preferences are required when registering, not required later

// 3 boolean values and 2 int values represent:
// social distancing preferred (true, false)
// air filtration preferred (true, false)
// outdoors only preferred (true, false)
// preferred maximum capacity
// preferred maximum venue size

    private static void register3Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "John Biggson",
                "jbiggson1@hotmail.co.uk",
                "077893153480",
                "jbiggson2",
                true,
                true,
                true,
                20,
                20
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Jane Giantsdottir",
                "jane@inf.ed.ac.uk",
                "04462187232",
                "giantsRverycool",
                true,
                false,
                true,
                50,
                50
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Wednesday Kebede",
                "i-will-kick-your@gmail.com",
                "-",
                "it is wednesday my dudes",
                false,
                false,
                false,
                100,
                220
        ));
        controller.runCommand(new LogoutCommand());
    }

    // Now, we test the values are all stored and function properly
    // We need to implement getResult in command
    @Test
    void getRegisteredConsumers(){

        GovernmentReport1Command cmd = new GovernmentReport1Command("Consumers");
        controller.runCommand(cmd);
        List<Consumer> consumers = cmd.getResult();

        assertEquals(2, consumers.size());
        assertTrue(consumers.stream().anyMatch(consumer -> consumer.getName().equals("John Biggson")));
        assertTrue(consumers.stream().anyMatch(consumer -> consumer.getName().equals("Jane Giantsdottir")));
    }
}
