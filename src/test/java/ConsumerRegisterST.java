import command.RegisterConsumerCommand;
import controller.Controller;
import model.Consumer;
import model.User;
import logging.Logger;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsumerRegisterST {

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
    void printTestName(TestInfo testInfo){
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();;
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    public void testName(){
        try{
            AssertionError expectedError = assertThrows(AssertionError.class, () -> {
                controller.runCommand(new RegisterConsumerCommand(null, TESTEMAIL,
                        TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL));
            }, "An assertion error should be raised for invalid user name");
            assertEquals("Name cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");
        }catch(Exception e){
            return;
        }

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testEmail(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterConsumerCommand(TESTNAME, null,
                    TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL));
        }, "An assertion error should be raised for invalid user email address");
        assertEquals("Email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testPhoneNumber(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterConsumerCommand(TESTNAME, TESTEMAIL,
                    null, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL));
        }, "An assertion error should be raised for invalid user phone number");
        assertEquals("Phone number cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testPassword(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterConsumerCommand(TESTNAME, TESTEMAIL,
                    TESTPHONENUMBER, null, TESTPAYMENTACCOUNTEMAIL));
        }, "An assertion error should be raised for invalid user password");
        assertEquals("Password cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testPaymentAccountEmail(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterConsumerCommand(TESTNAME, TESTEMAIL,
                    TESTPHONENUMBER, TESTPASSWORD, null));
        }, "An assertion error should be raised for invalid user payment account email");
        assertEquals("Payment account email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testDuplicateEmail(){
        RegisterConsumerCommand registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);
        controller.runCommand(registerConsumerCommand);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(registerConsumerCommand);
        }, "An assertion error should be raised for duplicate keys of user email address");
        assertEquals("Email already registered with another user.",
                expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterConsumerCommand.LogStatus.USER_REGISTER_EMAIL_ALREADY_REGISTERED);
    }

    @Test
    public void testSuccess(){
        RegisterConsumerCommand registerConsumerCommand = new RegisterConsumerCommand(TESTNAME,
                TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);
        Map<String, User> expectedUsers = Collections.emptyMap();
        Consumer testConsumer = new Consumer(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        assertDoesNotThrow(() -> { controller.runCommand(registerConsumerCommand); }, "Correct input should not raise any errors");

        //controller.runCommand(registerConsumerCommand);
        expectedUsers.put(testConsumer.getEmail(), testConsumer);
        assertEquals(testConsumer, registerConsumerCommand.getResult(), "Result should be registered consumer in success scenario");

        // update success log status
        Logger.getInstance().logAction(makeBanner("test on success case"), RegisterConsumerCommand.LogStatus.REGISTER_CONSUMER_SUCCESS);
    }
}
