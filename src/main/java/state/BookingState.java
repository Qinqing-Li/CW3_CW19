package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;
import java.time.LocalDateTime;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingState implements IBookingState {

    private BookingState bookingState;
    private List<Booking> bookings;
    private int bookingNumber;

    public BookingState() {
        this.bookings = new ArrayList<Booking>();
        this.bookingNumber = 1;
    }
    
    public BookingState(IBookingState other) {
        bookingState = null;
        try{
            bookingState = (BookingState) super.clone();
        }catch (CloneNotSupportedException e){
            // deep clone failed so we need to create a new instance
            bookingState = new BookingState();
        }
    }

    // returns booking by booking number or null if not present
    @Override
    public Booking findBookingByNumber(long bookingNumber) {
        for (Booking booking : bookings) {
            if (booking.getBookingNumber() == bookingNumber) {
                return booking;
            }
        }
        return null;
    }

    // gets all bookings under certain event
    @Override
    public List<Booking> findBookingsByEventNumber(long eventNumber) {
        List<Booking> eventBookings = new ArrayList<Booking>();
        for (Booking booking : bookings) {
            if (booking.getEventPerformance().getPerformanceNumber() == eventNumber) {
                eventBookings.add(booking);
            }
        }
        return eventBookings;
    }

    @Override
    public Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {
        Booking newBooking = new Booking(bookingNumber, booker, performance, numTickets, amountPaid, LocalDateTime.now(ZoneId.of("GMT")));
        bookings.add(newBooking);
        bookingNumber++;
        return newBooking;
    }
}
