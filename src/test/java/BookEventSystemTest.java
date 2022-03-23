import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import external.*;
import state.BookingState;

// This system test doesn't look at implementation of methods or how class communicate with each other
// The purpose is to feed in realistic inputs and test success case success, and fail case response
// with desirable error message.
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
// First, let's test the success scenario：

}
