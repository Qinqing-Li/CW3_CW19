package state;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventState implements IEventState {

    private List<Event> events;
    private int eventNumber;
    private int performanceNumber;

    public EventState() {
        this.events = new ArrayList<>();
        this.eventNumber = 1;
        this.performanceNumber = 1;
    }

    public EventState(IEventState other) {
        events = other.events;
        eventNumber = other.eventNumber;
        performanceNumber = other.performanceNumber;
    }

    @Override
    public List<Event> getAllEvents() {
        return events;
    }

    @Override
    public Event findEventByNumber(long eventNumber) {
        for (Event event : events) {
            if (event.getEventNumber() == eventNumber) {
                return event;
            }
        }
        return null;
    }

    @Override
    public EventPerformance createEventPerformance(Event event,
                                                   String venueAddress,
                                                   LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime,
                                                   List<String> performerNames,
                                                   boolean hasSocialDistancing,
                                                   boolean hasAirFiltration,
                                                   boolean isOutdoors,
                                                   int capacityLimit,
                                                   int venueSize) {
        EventPerformance newPerformance = new EventPerformance(performanceNumber,
                event,
                venueAddress,
                startDateTime,
                endDateTime,
                performerNames,
                hasSocialDistancing,
                hasAirFiltration,
                isOutdoors,
                capacityLimit,
                venueSize);
        event.addPerformance(newPerformance);
        performanceNumber++;
        return newPerformance;
    }

    @Override
    public NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser,
                                                   String title,
                                                   EventType type) {
        NonTicketedEvent newEvent = new NonTicketedEvent(eventNumber, organiser, title, type);
        events.add(newEvent);
        eventNumber++;
        return newEvent;
    }

    @Override
    public TicketedEvent createTicketedEvent(EntertainmentProvider organiser,
                                             String title,
                                             EventType type,
                                             double ticketPrice,
                                             int numTickets) {
        TicketedEvent newEvent = new TicketedEvent(eventNumber, organiser, title, type, ticketPrice, numTickets);
        events.add(newEvent);
        eventNumber++;
        return newEvent;
    }
}
