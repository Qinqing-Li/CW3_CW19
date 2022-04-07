package command;

import controller.Context;

public class LogoutCommand implements ICommand {

    public enum LogStatus{
        USER_LOGOUT_SUCCESS,
        USER_LOGOUT_NOT_LOGGED_IN
    }

    public LogoutCommand(){

    }

    @Override
    public void execute(Context context) {
        assert context.getUserState().getCurrentUser() != null : "User must be logged in.";
        context.getUserState().setCurrentUser(null);
    }

    @Override
    public Object getResult() {
        return null;
    }
}
