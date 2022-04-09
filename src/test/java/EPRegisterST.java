import command.RegisterConsumerCommand;
import logging.Logger;
import controller.Controller;
import command.RegisterEntertainmentProviderCommand;

import model.Consumer;
import model.EntertainmentProvider;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EPRegisterST {
    private static Controller controller;
    private final static String TESTORGNAME = "Nando";
    private final static String TESTORGADDRESS = "South Bridge";
    private final static String TESTPAYMENTACCOUNTEMAIL = "nando@co.uk";
    private final static String TESTMAINREPNAME = "David";
    private final static String TESTMAINREPEMAIL = "david@gmail.com";
    private final static String TESTPASSWORD = "24fhui23";
    private final static List<String> TESTOTHERREPNAMES = new ArrayList<String>(List.of("Suzy", "Hawk"));
    private final static List<String> TESTOTHERREPEMAILS = new ArrayList<String>(List.of("sewr@gmail.com", "hawk@gmail.com"));

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        controller = new Controller();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private String makeBanner(String testcaseName){
        return "##########################\n" +
                "test case name: " + testcaseName +
                "\n#########################";
    }

    @Test
    void testOrgname(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(null, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org name");
        assertEquals("Organisation name cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org name"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testOrgAddress(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, null,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org address");
        assertEquals("Organisation address cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org address"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testPaymentAccountEmail(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    null, TESTMAINREPNAME, TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org payment account email");
        assertEquals("Payment account email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org payment account email"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testMainRepName(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, null, TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org main rep name");
        assertEquals("Main representative name cannot be null.", expectedError.getMessage(),
                "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org main rep name"),
                RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testMainRepEmail(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, null,
                    TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org main rep email address");
        assertEquals("Main representative email cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org main rep email"),
                RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testPassword(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, TESTMAINREPEMAIL, null, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid password");
        assertEquals("Password cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null password"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testOtherRepNames(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, TESTMAINREPEMAIL, TESTPASSWORD, null, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for invalid org other rep names");
        assertEquals("Other representatives' names cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on null org other rep names"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    void testOtherRepEmails(){
        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(TESTORGNAME, TESTORGADDRESS,
                    TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME, TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, null));
        }, "An assertion error should be raised for invalid org other rep emails");
        assertEquals("Other representatives' emails cannot be null.", expectedError.getMessage(), "Assertion error message should be the same");


        Logger.getInstance().logAction(makeBanner("test on null org other rep emails"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
    }

    @Test
    public void testDuplicateEmail(){
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                TESTORGNAME, TESTORGADDRESS, TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME,
                TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));

        AssertionError expectedError = assertThrows(AssertionError.class, () -> {
            controller.runCommand(new RegisterEntertainmentProviderCommand(
                    TESTORGNAME, TESTORGADDRESS, TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME,
                    TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS));
        }, "An assertion error should be raised for duplicate keys of user email address");
        assertEquals("Main representative email already taken.", expectedError.getMessage(), "Assertion error message should be the same");

        Logger.getInstance().logAction(makeBanner("test on duplicate emails"), RegisterEntertainmentProviderCommand.LogStatus.USER_REGISTER_EMAIL_ALREADY_REGISTERED);
    }

    @Test
    public void testSuccess(){
        RegisterEntertainmentProviderCommand registerEntertainmentProviderCommand = new RegisterEntertainmentProviderCommand(
                TESTORGNAME, TESTORGADDRESS, TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME,
                TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS);

        assertDoesNotThrow(() -> {
            controller.runCommand(registerEntertainmentProviderCommand); }, "Correct input should not raise any errors");

        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(TESTORGNAME,
                TESTORGADDRESS, TESTPAYMENTACCOUNTEMAIL, TESTMAINREPNAME,
                TESTMAINREPEMAIL, TESTPASSWORD, TESTOTHERREPNAMES, TESTOTHERREPEMAILS);
        Map<String, User> expectedEPs = new HashMap<>();
        expectedEPs.put(entertainmentProvider.getEmail(), entertainmentProvider);

        EntertainmentProvider actualUser = (EntertainmentProvider) registerEntertainmentProviderCommand.getResult();
        assertAll(() -> assertEquals(entertainmentProvider.getEmail(), actualUser.getEmail(),
                        "Result email should be registered consumer email in success scenario"),
                () -> assertEquals(entertainmentProvider.getEvents(), actualUser.getEvents(),
                        "Result name should be registered consumer name in success scenario"),
                () -> assertEquals(entertainmentProvider.getPaymentAccountEmail(), actualUser.getPaymentAccountEmail(),
                        "Result payment account email should be registered consumer's payment account email in success scenario"),
                () -> assertEquals(entertainmentProvider.getOrgAddress(), actualUser.getOrgAddress(),
                        "Result list of bookings should be registered consumer's bookings in success scenario"),
                () -> assertEquals(entertainmentProvider.getOrgName(), actualUser.getOrgName(),
                        "Result list of bookings should be registered consumer's bookings in success scenario")
        );

        // update success log status
        Logger.getInstance().logAction(makeBanner("test on success case"), RegisterEntertainmentProviderCommand.LogStatus.REGISTER_ENTERTAINMENT_PROVIDER_SUCCESS);
    }
}
