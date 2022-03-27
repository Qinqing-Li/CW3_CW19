package external;

import model.Booking;
import model.TicketedEvent;

import java.net.CookieHandler;
import java.time.LocalDateTime;
import java.util.*;

public class MockEntertainmentProviderSystem implements EntertainmentProviderSystem {

    private String orgName;
    private String orgAddress;
    private Map<Long, Integer> eventTicketNumbers; // contains number of tickets available for each event
                                                // stored as eventNumber : numTickets
    private Map<Long, List<Long>> bookingNumbers; // contains number of tickets in a specific booking
                                                  // stored as bookingNumber : [numTickets, eventNumber]

    public MockEntertainmentProviderSystem(String orgName, String orgAddress){
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.eventTicketNumbers = Collections.emptyMap();
        this.bookingNumbers = Collections.emptyMap();
    }

    @Override
    public void cancelBooking (long bookingNumber){
        // increase number of tickets available for event
        long eventNum = bookingNumbers.get(bookingNumber).get(1);
        long newTicketsAvailable = eventTicketNumbers.get(eventNum) + bookingNumbers.get(bookingNumber).get(0);
        eventTicketNumbers.put(eventNum, (int) newTicketsAvailable);
        // delete booking from record
        bookingNumbers.remove(bookingNumber);
    }

    @Override
    public void	cancelEvent (long eventNumber, String message){
        eventTicketNumbers.remove(eventNumber);

        // removes all bookings with relevant event number
        bookingNumbers.values().removeIf(val -> val.get(1)==eventNumber);
    }

    @Override
    public int getNumTicketsLeft (long eventNumber, long performanceNumber){
        return eventTicketNumbers.get(eventNumber);
    }

    @Override
    public void	recordNewBooking (long eventNumber, long performanceNumber,
                                     long bookingNumber, String consumerName, String consumerEmail, int bookedTickets){
        // add booking to booking hashmap
        List<Long> newValuePair = new ArrayList<>();
        newValuePair.add((long) bookedTickets);
        newValuePair.add(eventNumber);
        bookingNumbers.put(eventNumber, newValuePair);

        // decrease tickets available
        int newTicketsAvailable = (int) eventTicketNumbers.get(eventNumber) - bookedTickets;
        eventTicketNumbers.put(eventNumber, newTicketsAvailable);
    }

    @Override
    public void	recordNewEvent (long eventNumber, String title, int numTickets){
        eventTicketNumbers.put(eventNumber, numTickets);
    }

    @Override
    public void	recordNewPerformance (long eventNumber, long performanceNumber,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime){
        // not necessary to implement to count event tickets
    }

    @Override
    public void	recordSponsorshipAcceptance (long eventNumber, int sponsoredPricePercent){
        recordSponsorshipAcceptance(eventNumber, sponsoredPricePercent);
    }

    @Override
    public void	recordSponsorshipRejection (long eventNumber){
        recordSponsorshipRejection(eventNumber);
    }
}


