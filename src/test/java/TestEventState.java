import jdk.swing.interop.SwingInterOpUtils;
import model.*;

import org.junit.jupiter.api.*;
import state.EventState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestEventState {

    private EventState eventState;
    private EntertainmentProvider genericProvider;
    private EntertainmentProvider genericProvider2;

    @BeforeAll
    @DisplayName("Creating instances of local variables")
    public void createInstance(){
        this.eventState = new EventState();

        genericProvider = new EntertainmentProvider("Comedy club",
                "comedyclub@gmail.com",
                "comedypaypal@gmail.com",
                "Chris Rock",
                "chrisrock@gmail.com",
                "password123",
                Collections.emptyList(),
                Collections.emptyList());

        this.genericProvider2 = new EntertainmentProvider("Dance club",
                "danceclub@gmail.com",
                "dancepaypal@gmail.com",
                "Sir Bruce Forsyth",
                "bruce@gmail.com",
                "seven777",
                Collections.emptyList(),
                Collections.emptyList());
    }

    @Test
    @DisplayName("Testing the initialised EventState")
    void testInitialization(){

        this.eventState = new EventState();

        assertAll(
                () -> assertEquals(eventState.getAllEvents(), Collections.emptyList(),
                        "Initial event list should be empty."),
                () -> Assertions.assertNull(eventState.findEventByNumber(1),
                        "Find event by number should be null when event not present.")
        );
    }

    @Test
    @DisplayName("Testing the adding of a ticketed event.")
    void testAddTicketedEvent() {

        TicketedEvent eventTest = eventState.createTicketedEvent(genericProvider,
                "Comedy Night",
                EventType.Theatre,
                20,
                300);

        assertAll(
                () -> assertEquals(eventState.getAllEvents().size(), 1,
                        "Should be one item in event list."),
                () -> Assertions.assertNotNull(eventState.findEventByNumber(1),
                        "Should return the created event with event number 1, but null is returned instead."),
                () -> Assertions.assertSame(eventTest, eventState.findEventByNumber(1),
                        "Newly created TicketedEvent should be present in EventState but isn't.")
        );
    }

    @Test
    @DisplayName("Testing the adding of a non ticketed event.")
    void testAddNonTicketedEvent() {

        NonTicketedEvent newNonTicketedEvent = eventState.createNonTicketedEvent(genericProvider2,
                "Dance Club",
                EventType.Dance);

        System.out.println("STATE SIZE: " + eventState.getAllEvents().size());
        assertAll(
                () -> assertEquals(eventState.getAllEvents().size(), 2,
                        "Event state should have 2 items but has " + eventState.getAllEvents().size()),
                () -> Assertions.assertNotNull(eventState.findEventByNumber(2),
                        "Should return the created event with event number 2, but null is returned instead."),
                () -> Assertions.assertSame(eventState.findEventByNumber(2), newNonTicketedEvent,
                        "Newly created newNonTicketedEvent should be present in EventState but isn't.")
        );
        System.out.println("All events1: " + eventState.getAllEvents());
    }

    @Test
    @DisplayName("Testing the adding of an event performance.")
    void testCreateEventPerformance() {
        List<String> performerNames = new ArrayList<>();
        performerNames.add("Chris Rock");
        System.out.println("All events: " + eventState.getAllEvents());
        EventPerformance newPerformance = eventState.createEventPerformance(eventState.findEventByNumber(1),
                "7 comedy road",
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(1),
                performerNames,
                true,
                true,
                false,
                100,
                300);

        assertAll(
                () -> Assertions.assertNotNull(
                        eventState.findEventByNumber(1).getPerformanceByNumber(1),
                        "Performance should be present in event 1 under performance number 1 but is null."),
                () -> assertEquals(eventState.findEventByNumber(1).getPerformances().size(), 1,
                        "Number of performances in event number 1 should be 1 but is " +
                                eventState.findEventByNumber(1).getPerformances().size()),
                () -> Assertions.assertSame(
                        eventState.findEventByNumber(1).getPerformanceByNumber(1),
                        newPerformance,
                        "The returned performance should be the same as the one that was added but is not.")
        );

    }

}
