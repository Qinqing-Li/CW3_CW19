import model.GovernmentRepresentative;
import model.User;
import org.junit.jupiter.api.*;
import state.UserState;
import model.Consumer;
import model.EntertainmentProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserState {
    private static UserState userState;
    private static Map<String, User> expectedUsers;
    private static GovernmentRepresentative randomRep1;
    private static GovernmentRepresentative randomRep2;
    private static GovernmentRepresentative randomRep3;
    private static GovernmentRepresentative randomRep4;

    @BeforeEach
    void createInstance(TestInfo testInfo){
        System.out.println(testInfo.getDisplayName());

        userState = new UserState();
        expectedUsers = new HashMap<String, User>();

        // add existing government reps to the list of users
        randomRep1 = new GovernmentRepresentative("Suzy", "wrui2fnk",
                "suzy@123.com");
        randomRep2 = new GovernmentRepresentative("David", "qwejr9io2i3",
                "david@123.com");
        randomRep3 = new GovernmentRepresentative("hii", "waejrowj",
                "hii@123.com");
        randomRep4 = new GovernmentRepresentative("margaret.thatcher@gov.uk",
                "The Good times  ",
                "margaret@123.com"
        );

        expectedUsers.put(randomRep1.getEmail(), randomRep1);
        expectedUsers.put(randomRep2.getEmail(), randomRep2);
        expectedUsers.put(randomRep3.getEmail(), randomRep3);
        expectedUsers.put(randomRep4.getEmail(), randomRep4);
    }

    @Test
    void testInitialization(){
        assertAll("Test UserState initialization",
                () -> assertEquals(4, userState.getAllUsers().size(),
                        "The users upon initialize should have 3 fixed government representatives accounts"),
                () -> assertNull( userState.getCurrentUser(),
                "The user upon initialization should be null"));
    }

    @Test
    void addConsumer(){
        Consumer consumer1 = new Consumer("john", "john@gmail.com", "123448888",
                "john#password", "johnPayment@gmail.com");
        Consumer consumer2 = new Consumer("hiii", "hiiiiii@email.com", "123448888",
                "hiii-password", "hiiii@gmail.com");

        expectedUsers.put(consumer1.getEmail(), consumer1);
        expectedUsers.put(consumer2.getEmail(), consumer2);

        userState.addUser(consumer1);
        userState.addUser(consumer2);

        assertAll("Test add user with Consumer",
                () -> assertEquals(6, userState.getAllUsers().size(),
                        "There should be 5 users now with 3 government reps and 2 consumers"),
                () -> assertSame(consumer1, userState.getAllUsers().get(consumer1.getEmail()),
                        "The consumer1 details should be set into the user list"),
                () -> assertSame(consumer2, userState.getAllUsers().get(consumer2.getEmail()),
                        "The consumer2 details should be set into the user list")
        );

    }

    @Test
    void addEP(){
        EntertainmentProvider ep1 = new EntertainmentProvider(
                "org1",
                "orgaddr",
                "orgpaymentaccount",
                "rep1",
                "repemail1",
                "gwafijg",
                List.of("Unknown Actor", "Spy"),
                List.of("unknown@gmail.com", "spy@gmail.com")
        );

        EntertainmentProvider ep2 = new EntertainmentProvider(
                "org2",
                "orgaddr",
                "john@paymentaccount",
                "john",
                "john@gmail.com",
                "gibberish",
                List.of("Unknown Actor", "Spy"),
                List.of("unknown@gmail.com", "spy@gmail.com")
        );

        expectedUsers.put(ep1.getEmail(), ep1);
        expectedUsers.put(ep2.getEmail(), ep2);

        userState.addUser(ep1);
        userState.addUser(ep2);

        assertAll("Test add user with Consumer",
                () -> assertEquals(6, userState.getAllUsers().size(),
                        "There should be 5 users now with 3 government reps and 2 consumers"),
                () -> assertSame(ep1, userState.getAllUsers().get(ep1.getEmail()),
                        "The entertainment provider1's details should be set into the user list"),
                () -> assertSame(ep2, userState.getAllUsers().get(ep2.getEmail()),
                        "The entertainment provider2's details should be set into the user list")
        );
    }

    @Test
    void testCurrentUser() {
        userState.setCurrentUser(randomRep1);
        User actualUser = userState.getCurrentUser();

        assertAll("Test on set and get current users",
                () -> assertTrue(actualUser instanceof GovernmentRepresentative,
                        "The current user should be a class of GovernmentRepresentative"),
                () -> assertSame(randomRep1, actualUser,
                        "The current set user should refer to the same GovernmentRepresentative instance"));
    }
}
