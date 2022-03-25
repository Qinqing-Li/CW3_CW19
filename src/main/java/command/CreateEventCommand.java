package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventType;
import model.User;

public abstract class CreateEventCommand implements ICommand {

    protected final String title;
    protected final EventType type;
    protected Long eventNumberResult;

    public CreateEventCommand(String title, EventType type){

        this.title = title;
        this.type = type;

    }


    protected boolean isUserAllowedToCreateEvent(Context context) {
        // Is user logged in?
        if (context.getUserState().getCurrentUser() == null) {
            return false;
        }

        // Is user entertainment provider?
        User currentUser = context.getUserState().getCurrentUser();
        return currentUser instanceof EntertainmentProvider;

    }

    public Long getResult() {
        return eventNumberResult;
    }

}
