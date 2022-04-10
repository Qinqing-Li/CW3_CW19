package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand implements ICommand {

    private EventPerformance result;
    private long eventNumber;
    private String venueAddress;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<String> performerNames;
    private boolean hasSocialDistancing;
    private boolean hasAirFiltration;
    private boolean isOutdoors;
    private int capacityLimit;
    private int venueSize;


    public AddEventPerformanceCommand(long eventNumber,
                                      String venueAddress,
                                      LocalDateTime startDateTime,
                                      LocalDateTime endDateTime,
                                      List<String> performerNames,
                                      boolean hasSocialDistancing,
                                      boolean hasAirFiltration,
                                      boolean isOutdoors,
                                      int capacityLimit,
                                      int venueSize){

    this.eventNumber = eventNumber;
    this.venueAddress = venueAddress;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.performerNames = performerNames;
    this.hasSocialDistancing = hasSocialDistancing;
    this.hasAirFiltration = hasAirFiltration;
    this.isOutdoors = isOutdoors;
    this.capacityLimit = capacityLimit;
    this.venueSize = venueSize;

    }

     @Override
    public void execute(Context context) {
        result = null;
        assert startDateTime.isBefore(endDateTime) : "Start date/time must be before end date/time.";
        assert capacityLimit >= 1 : "Capacity limit must be at least 1.";
        assert venueSize >= 1 : "Venue size must be at least 1.";
        assert context.getUserState().getCurrentUser() instanceof EntertainmentProvider :
                "Logged in user must be an entertainment provider.";
        assert context.getEventState().findEventByNumber(eventNumber) != null :
                "Event number must correspond to an existing event.";
        EntertainmentProvider entertainmentProvider = (EntertainmentProvider) context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(eventNumber);
        assert entertainmentProvider.getEvents().contains(event) : "Current user must be the event organiser.";

        boolean unique = true;
        for (Event e : context.getEventState().getAllEvents()) {
            if (e.getEventNumber() != event.getEventNumber()) {
                if (e.getTitle().equals(event.getTitle())) {
                    for (EventPerformance p : e.getPerformances()) {
                        if ((p.getStartDateTime().equals(startDateTime)) && (p.getEndDateTime().equals(endDateTime))) {
                            unique = false;
                            break;
                        }
                    }
                }
            }
            if (!unique) {break;}
        }
        assert unique : "You may not add a performance with the same event title and the same start time and end time.";

        EventPerformance performance = context.getEventState().createEventPerformance(event,
                venueAddress,
                startDateTime,
                endDateTime,
                performerNames,
                hasSocialDistancing,
                hasAirFiltration,
                isOutdoors,
                capacityLimit,
                venueSize);

        result = performance;

    }

    @Override
    public EventPerformance getResult() {
        return result;
    }

}
