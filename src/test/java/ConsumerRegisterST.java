import command.RegisterConsumerCommand;
import controller.Context;
import model.Consumer;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ConsumerRegisterST {

    private static Logger logger;
    private static RegisterConsumerCommand registerConsumerCommand;
    private static Context context;
    private static Map<String, User> expectedUsers;
    private static Consumer testConsumer;
    private final static String TESTNAME = "john";
    private final static String TESTEMAIL = "john@gmail.com";
    private final static String TESTPHONENUMBER = "08888654321";
    private final static String TESTPASSWORD = "john#password";
    private final static String TESTPAYMENTACCOUNTEMAIL = "john123@gmail.com";

    @BeforeAll
    public static void initialize(){
        logger = Logger.getLogger("Consumer Register System-level Test");
        // Ensure that the logger will capture all messages from all logging levels (from FINEST to SEVERE)
        logger.setLevel(Level.ALL);

        context = new Context();
        expectedUsers = Collections.emptyMap();
        testConsumer = new Consumer(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);
    }

    private String makeBanner(String testcaseName){
        return  "###############################\n" +
                "Test case name: " + testcaseName +
                "\n###############################\n";
    }

    @Test
    public void testName(){
        logger.info(makeBanner("test for invalid name"));
        registerConsumerCommand = new RegisterConsumerCommand(null, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for invalid user name");
        assertEquals("Name cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    public void testEmail(){
        logger.info(makeBanner("test for invalid email address"));
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, null, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for invalid user email address");
        assertEquals("Email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    public void testPhoneNumber(){
        logger.info(makeBanner("test for invalid phone number"));
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, null, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for invalid user phone number");
        assertEquals("Phone number cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

    }

    @Test
    public void testPassword(){
        logger.info(makeBanner("test for invalid password"));
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, null, TESTPAYMENTACCOUNTEMAIL);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for invalid user password");
        assertEquals("Password cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    public void testPaymentAccountEmail(){
        logger.info(makeBanner("test for invalid payment account email"));
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, null);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for invalid user payment account email");
        assertEquals("Payment account email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    public void testDuplicateEmail(){
        logger.info(makeBanner("test for duplicate user email addresses"));
        context.getUserState().addUser(testConsumer);
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            registerConsumerCommand.execute(context);
        }, "An assertion error should be raised for duplicate keys of user email address");
        assertEquals("Email already registered with another user.", expectedError.getMessage(), "Assertion error message should be the same");
    }

    @Test
    public void testSuccess(){
        logger.info(makeBanner("test for successful user register scenario"));
        registerConsumerCommand = new RegisterConsumerCommand(TESTNAME, TESTEMAIL, TESTPHONENUMBER, TESTPASSWORD, TESTPAYMENTACCOUNTEMAIL);
        assertDoesNotThrow(() -> { registerConsumerCommand.execute(context); }, "Correct input should not raise any errors");

        registerConsumerCommand.execute(context);
        expectedUsers.put(testConsumer.getEmail(), testConsumer);

        assertAll(
                () -> assertEquals(expectedUsers, context.getUserState().getAllUsers(), "User should be added to the list of registered users in success scenario"),
                () -> assertEquals(testConsumer, registerConsumerCommand.getResult(), "Result should be registered consumer in success scenario")
        );
    }

    @AfterEach
    public void resetRegisterCommand(){
        registerConsumerCommand = null;
        context = null;
    }
}
