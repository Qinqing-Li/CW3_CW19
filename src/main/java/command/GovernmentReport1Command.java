package command;

import controller.Context;
import model.Booking;
import model.BookingStatus;
import model.Event;
import model.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GovernmentReport1Command implements ICommand {

    private LocalDateTime intervalStartInclusive;
    private LocalDateTime intervalEndInclusive;
    private List<Booking> result;

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {
        this.intervalEndInclusive = intervalEndInclusive;
        this.intervalStartInclusive = intervalStartInclusive;
        this.result = new ArrayList<>();
    }

    public void execute(Context context) {
        List<Booking> bookings = new ArrayList<>();

        for (Event event : context.getEventState().getAllEvents()) {
            //list all valid bookings for individual event
            ListEventBookingsCommand listBookings = new ListEventBookingsCommand(event.getEventNumber());
            listBookings.execute(context);
            List<Booking> eventBookings = listBookings.getResult();

            // only add bookings which occur within timeframe
            for (Booking booking : eventBookings) {
                LocalDateTime startDate = booking.getEventPerformance().getStartDateTime();
                LocalDateTime endDate = booking.getEventPerformance().getEndDateTime();
                if ((startDate.isAfter(intervalStartInclusive) || startDate.isEqual(intervalStartInclusive))
                && (endDate.isBefore(intervalEndInclusive) || endDate.isEqual(intervalEndInclusive))
                && booking.getEventPerformance().getEvent().getStatus() == EventStatus.ACTIVE) {
                    bookings.add(booking);
                    // Note: since we haven't implemented sponsorship acceptance/rejection (as a group of 3),
                    // we are unable to check if the event is sponsored or not, so we omitted it from the requirements
                }
            }
        }
        result = bookings;
    }

    public List<Booking> getResult() {
        return result;
    }

}
