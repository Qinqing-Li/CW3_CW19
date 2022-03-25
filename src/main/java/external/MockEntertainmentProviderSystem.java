package external;

import java.time.LocalDateTime;

public class MockEntertainmentProviderSystem implements EntertainmentProviderSystem {
    private String orgName;
    private String orgAddress;

    public MockEntertainmentProviderSystem(String orgName, String orgAddress){
        this.orgName = orgName;
        this.orgAddress = orgAddress;
    }

    // just calling functions from the interface as an API?
    @Override
    public void cancelBooking (long bookingNumber){
        cancelBooking(bookingNumber);
    }

    @Override
    public void	cancelEvent (long eventNumber, String message){
        cancelEvent(eventNumber, message);
    }

    @Override
    public int getNumTicketsLeft (long eventNumber, long performanceNumber){
        return getNumTicketsLeft(eventNumber, performanceNumber);
    }

    @Override
    public void	recordNewBooking (long eventNumber, long performanceNumber,
                                     long bookingNumber, String consumerName, String consumerEmail, int bookedTickets){
        recordNewBooking(eventNumber, performanceNumber, bookingNumber, consumerName, consumerEmail, bookedTickets);
    }

    @Override
    public void	recordNewEvent (long eventNumber, String title, int numTickets){
        recordNewEvent(eventNumber, title, numTickets);
    }

    @Override
    public void	recordNewPerformance (long eventNumber, long performanceNumber,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime){
        recordNewPerformance(eventNumber, performanceNumber, startDateTime, endDateTime);
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


