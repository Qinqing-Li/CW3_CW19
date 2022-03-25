package state;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IEventState {
    public List<Event> events = new ArrayList<>();

    public int eventNumber = 1;

    public int performanceNumber = 1;

    public List<Event> getAllEvents();

    Event findEventByNumber(long eventNumber);

    public EventPerformance createEventPerformance(Event event,
                                                   String venueAddress,
                                                   LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime,
                                                   List<String> performerNames,
                                                   boolean hasSocialDistancing,
                                                   boolean hasAirFiltration,
                                                   boolean isOutdoors,
                                                   int capacityLimit,
                                                   int venueSize);

    NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser,
                                             String title,
                                             EventType type);

    TicketedEvent createTicketedEvent(EntertainmentProvider organiser,
                                       String title,
                                       EventType type,
                                       double ticketPrice,
                                       int numTickets);

}
