package command;

import controller.Context;
import model.Consumer;
import model.ConsumerPreferences;
import model.EntertainmentProvider;
import model.Event;

import java.util.ArrayList;
import java.util.List;

public class ListEventsCommand implements ICommand {

    private List<Event> result;
    private boolean userEventsOnly;
    private boolean activeEventsOnly;

    public ListEventsCommand(boolean userEventsOnly, boolean activeEventsOnly) {
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
    }

    public void execute(Context context) {
        result = new ArrayList<Event>();
        for (Event event : context.getEventState().getAllEvents()) {
            boolean added = false;
            if (userEventsOnly) {
                if (context.getUserState().getCurrentUser() instanceof EntertainmentProvider) {
                    if (event.getOrganiser() == context.getUserState().getCurrentUser()) {
                        result.add(event);
                    }
                } else if (context.getUserState().getCurrentUser() instanceof Consumer) {
                    Consumer currentConsumer = (Consumer) context.getUserState().getCurrentUser();
                    ConsumerPreferences preferences = currentConsumer.getPreferences();
                    // TODO how should preferences be compared with all performances on a single event
                    // e.g. should preferences satisfy all performances or just one (assuming they're the same)
                    // all of the performances
                }
            }
            if (!added && activeEventsOnly) {

            }
        }
    }

    public List<Event> getResult() {
        return result;
    }

}
