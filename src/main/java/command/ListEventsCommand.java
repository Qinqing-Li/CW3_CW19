package command;

import controller.Context;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.EventStatus.ACTIVE;

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
        User currentUser = context.getUserState().getCurrentUser();
        for (Event event : context.getEventState().getAllEvents()) {
            boolean added = false;
            if (userEventsOnly) {
                if (currentUser instanceof EntertainmentProvider) {
                    if (event.getOrganiser() == currentUser) {result.add(event);}
                } else if (context.getUserState().getCurrentUser() instanceof Consumer) {
                    Consumer currentConsumer = (Consumer) context.getUserState().getCurrentUser();
                    ConsumerPreferences preferences = currentConsumer.getPreferences();
                    boolean satisfied = true;
                    for (EventPerformance performance : event.getPerformances()) {
                        if (! ((performance.hasAirFiltration() || !preferences.preferAirFiltration)
                        && (performance.hasSocialDistancing() || !preferences.preferSocialDistancing)
                        && (performance.isOutdoors() || !preferences.preferOutdoorsOnly)
                        && performance.getCapacityLimit() <= preferences.preferredMaxCapacity
                        && performance.getVenueSize() <= preferences.preferredMaxVenueSize)) {
                            satisfied = false;
                            break;
                        }
                    }
                    if (satisfied) { result.add(event); }
                }
            }
            if (!added && (!activeEventsOnly || event.getStatus() == ACTIVE)) {
                    result.add(event);
            }
        }
    }

    public List<Event> getResult() {
        return result;
    }

}
