package command;

import controller.Context;
import model.EventType;

public abstract class CreateEventCommand implements ICommand {

    protected final String title;
    protected final EventType type;
    protected Long eventNumberResult;

    public CreateEventCommand(String title, EventType type){

        this.title = title;
        this.type = type;

    }


    protected boolean isUserAllowedToCreateEvent(Context context) {
        return true;
    }

    public Long getResult() {
        return null;
    }

}
