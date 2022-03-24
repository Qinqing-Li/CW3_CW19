package command;

import controller.Context;
import model.Event;

import java.time.LocalDateTime;
import java.util.List;

public class ListEventsOnGivenDateCommand extends ListEventsCommand {

    public ListEventsOnGivenDateCommand(boolean userEventsOnly, boolean activeEventsOnly, LocalDateTime searchDateTime) {

        super(userEventsOnly, activeEventsOnly);

    }

    public void execute(Context context) {

    }

    public List<Event> getResult() {
        return null;
    }

}
