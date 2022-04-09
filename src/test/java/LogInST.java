import command.LoginCommand;
import command.RegisterConsumerCommand;
import controller.Controller;
import logging.Logger;

import model.Consumer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LogInST {

    private static Controller controller;
    private final static String TESTNAME = "john";
    private final static String TESTEMAIL = "john@gmail.com";
    private final static String TESTPHONENUMBER = "08888654321";
    private final static String TESTPASSWORD = "john#password";
    private final static String TESTPAYMENTACCOUNTEMAIL = "john123@gmail.com";

    private String makeBanner(String testcaseName){
        return "##########################\n" +
                "test case name: " + testcaseName +
                "\n#########################";
    }

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();
        Logger.getInstance();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void testOnNonExistedUser(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new LoginCommand(TESTEMAIL, TESTPASSWORD));
        }, "An assertion error should be raised for non-existed user email");
        assertEquals("Account email is not registered on the system.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on non-existed user email"), LoginCommand.LogStatus.USER_LOGIN_EMAIL_NOT_REGISTERED);
    }

    @Test
    void testWrongPassword(){
        controller.runCommand(new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL));

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new LoginCommand(TESTEMAIL, "random gibberish"));
        }, "An assertion error should be raised for wrong user password");
        assertEquals("Password does not match email.", expectedError.getMessage(), "Assertion error message should be the same");


        Logger.getInstance().logAction(makeBanner("test on wrong user password"), LoginCommand.LogStatus.USER_LOGIN_WRONG_PASSWORD);
    }

    @Test
    void testSuccess(){
        controller.runCommand(new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL));
        LoginCommand loginCommand = new LoginCommand(TESTEMAIL, TESTPASSWORD);
        Consumer consumer = new Consumer(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        assertDoesNotThrow(() -> { controller.runCommand(loginCommand); }, "Correct input should not raise any errors");
        Consumer settedResult = (Consumer) loginCommand.getResult();
        assertEquals(consumer.getEmail(), settedResult.getEmail(),
                "Current loged in user email should be set to the new user email");
        assertEquals(consumer.getName(), settedResult.getName(),
                "Current loged in user name should be set to the new user name");
        assertEquals(consumer.getPaymentAccountEmail(), settedResult.getPaymentAccountEmail(),
                "Current loged in user payment account email should be set to the new user payment account email");


        Logger.getInstance().logAction(makeBanner("test on login success case"), LoginCommand.LogStatus.USER_LOGIN_SUCCESS);
    }
}
