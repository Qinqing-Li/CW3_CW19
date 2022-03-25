package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;

import java.util.HashMap;
import java.util.List;

public interface IBookingState {

    public Booking findBookingByNumber(long bookingNumber);

    public List<Booking> findBookingsByEventNumber(long eventNumber);

    public Booking createBooking(Consumer booker,
                                  EventPerformance performance,
                                  int numTickets,
                                  double amountPaid);

}
