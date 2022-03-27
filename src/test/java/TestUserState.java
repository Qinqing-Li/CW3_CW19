
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import state.UserState;
import model.Consumer;
import model.EntertainmentProvider;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserState {
    private UserState userState;
    private Map<String, User> currentUsers;
    private User currentUser;

    @BeforeAll
    @DisplayName("UserState Test")
    void createInstance(){
        userState = new UserState();
        currentUsers = Collections.emptyMap();
    }

    @Test
    void testInitialization(){
        assertAll("Test UserState initialization",
                () -> assertEquals(Collections.emptyMap(), userState.getAllUsers(),
                        "The users upon initialize should be an empty hashmap"),
                () -> assertNull( userState.getCurrentUser(),
                "The user upon initialization should be null"));
    }

    @Test
    void addUser() {
        Consumer testUser1 = new Consumer("John", "john@gmail.com", "123488889",
                "John@password", "johnPaymentAccount");
        userState.addUser(testUser1);
        currentUsers.put(testUser1.getEmail(), testUser1);
        Map<String, User> users = userState.getAllUsers();
        User actualUser = users.get("john@gmail.com");

        assertAll("Test addUser method in UserState",
                () -> assertNotNull(users.get("john@gmail.com")),
                        "The instance john should be added to the users",
                () -> assertEquals(testUser1, users.get("john@gmail.com"), "The user instance should be the same"),
                () -> assertEquals("johnPaymentAccount", actualUser.getPaymentAccountEmail(), "The user's payment account should be the same"),
                () -> assertEquals("john@gmail.com", actualUser.getEmail(), "The user's email address should be the same"));
    }

    @Test
    void getAllUsers() {

    }

    @Test
    void getCurrentUser() {
        assertEquals(currentUser, userState.getCurrentUser(), "The currentUser should be the same as ")

    }

    @Test
    @DisplayName("Test setCurrentUser method in UserState\n")
    void setCurrentUser() {
        currentUser = new Consumer("John", "john@gmail.com", "123488889",
                "John@password", "johnPaymentAccount");
        userState.setCurrentUser(currentUser);

        assertTrue(userState.getCurrentUser() instanceof Consumer, "The current user should be a instance of Consumer");

        assertEquals(currentUser, userState.getCurrentUser(), "The currentUser should be set as the passed in User instance ")
    }
}
