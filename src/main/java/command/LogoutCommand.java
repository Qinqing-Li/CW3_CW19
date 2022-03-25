package command;

import controller.Context;

public class LogoutCommand implements ICommand {

    // TODO related problems?
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
