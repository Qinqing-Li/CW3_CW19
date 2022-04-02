package state;

import model.User;

import java.util.Collections;
import java.util.Map;

public interface IUserState {
    public Map<String, User> users = Collections.emptyMap();

    public User currentUser = null;

    public void addUser(User user);

    public Map<String,User> getAllUsers();

    public User getCurrentUser();

    public void setCurrentUser(User user);
}
