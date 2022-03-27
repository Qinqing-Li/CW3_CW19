package state;

import model.User;

import java.util.Collections;
import java.util.Map;

public class UserState implements IUserState {

    // map containing all users
    private Map<String, User> users;
    private User currentUser;

    public UserState() {
        this.users = Collections.emptyMap();
        this.currentUser = null;
    }

    public UserState(IUserState other) {
        this.users = other.users;
        this.currentUser = other.currentUser;
    }

    // TODO: check is the users Map key the user's email or something else?
    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
