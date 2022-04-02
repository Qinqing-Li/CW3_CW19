package state;

import model.GovernmentRepresentative;
import model.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserState implements IUserState, Cloneable {

    // map containing all users
    private Map<String, User> users;
    private User currentUser;
    private List<GovernmentRepresentative> governmentRepresentatives;

    public UserState() {
        this.users = Collections.emptyMap();
        this.currentUser = null;
        // add existing government representatives to the list of users (no registration)
        registerGovernmentRepresentatives();
    }

    public UserState(IUserState other) {
        UserState clonedState;
        UserState tempState = (UserState) other;
        try{
            clonedState = (UserState) tempState.clone();
        }catch (CloneNotSupportedException e){
            // call default constructor when the deep clone is failed
            clonedState = new UserState();
        }

        this.users = clonedState.users;
        this.currentUser = clonedState.currentUser;
        registerGovernmentRepresentatives();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    private void registerGovernmentRepresentatives(){
        // add some random email/password/payment account email government representative instance to the users
        GovernmentRepresentative randomRep1 = new GovernmentRepresentative("Suzy", "wrui2fnk",
                "suzy@123.com");
        GovernmentRepresentative randomRep2 = new GovernmentRepresentative("David", "qwejr9io2i3",
                "david@123.com");
        GovernmentRepresentative randomRep3 = new GovernmentRepresentative("hii", "waejrowj",
                "hii@123.com");

        users.put(randomRep1.getEmail(), randomRep1);
        users.put(randomRep2.getEmail(), randomRep2);
        users.put(randomRep3.getEmail(), randomRep3);
    }

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
