package command;

import controller.Context;
import model.Event;
import model.EventPerformance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListEventsOnGivenDateCommand extends ListEventsCommand {

    private List<Event> result;
    private LocalDateTime searchDateTime;

    public ListEventsOnGivenDateCommand(boolean userEventsOnly,
                                        boolean activeEventsOnly,
                                        LocalDateTime searchDateTime) {

        super(userEventsOnly, activeEventsOnly);
        this.searchDateTime = searchDateTime;

    }

    public void execute(Context context) {
        super.execute(context);
        List<Event> preliminaryList = super.getResult();

        // filter preliminary results by date
        List<Event> eventsWithValidDates = new ArrayList<Event>();
        for (Event event : preliminaryList) {
            for (EventPerformance performance : event.getPerformances()) {
                if (performance.getStartDateTime().minusDays(1).compareTo(searchDateTime) > 0) {
                    eventsWithValidDates.add(event);
                    break;
                }
            }
        }
        result = eventsWithValidDates;
    }

    public List<Event> getResult() {
        return result;
    }

}
