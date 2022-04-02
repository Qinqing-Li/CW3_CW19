package command;

import controller.Context;
import model.User;

public class LoginCommand implements ICommand {

    private String email;
    private String password;
    private User result;

    public enum LogStatus{
        USER_LOGIN_SUCCESS,
        USER_LOGIN_EMAIL_NOT_REGISTERED,
        USER_LOGIN_WRONG_PASSWORD
    }

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void execute(Context context) {
        assert context.getUserState().getAllUsers().containsKey(email) :
                "Account email is not registered on the system.";
        User currentUser = context.getUserState().getAllUsers().get(email);
        assert currentUser.checkPasswordMatch(password) :
                "Password does not match email.";

        context.getUserState().setCurrentUser(currentUser);
        result = currentUser;
    }

    @Override
    public Object getResult() {
        return result;
    }
}
