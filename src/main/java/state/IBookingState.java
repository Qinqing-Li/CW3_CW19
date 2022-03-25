package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;

import java.util.ArrayList;
import java.util.List;

public interface IBookingState {
    public List<Booking> bookings = new ArrayList<>();

    public int bookingNumber = 1;
    
    public Booking findBookingByNumber(long bookingNumber);

    public List<Booking> findBookingsByEventNumber(long eventNumber);

    public Booking createBooking(Consumer booker,
                                  EventPerformance performance,
                                  int numTickets,
                                  double amountPaid);

}
