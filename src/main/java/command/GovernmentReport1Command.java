package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>For Government to request report(s)</h1>
 *
 * We implement Request Booking Records use case, where the government
 * asks for specific information about existing bookings or consumers.
 * The record will return two sets of dates and times.
 *
 * @author  CW19
 */

public class GovernmentReport1Command implements ICommand {

    private LocalDateTime intervalStartInclusive;
    private LocalDateTime intervalEndInclusive;
    private List<Booking> result;

    /**
     * This method get two time stamps and create an arraylist to be used later
     * @param intervalStartInclusive First parameter, beginning time (inclusive)
     * @param intervalEndInclusive Second parameter, ending time (inclusive)
     * @return void
     */
    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {
        this.intervalEndInclusive = intervalEndInclusive;
        this.intervalStartInclusive = intervalStartInclusive;
        this.result = new ArrayList<>();
    }

    /**
     * This method first check user is government representative (only Gov rep are allowde this action)
     * First, create an new arraylist booking. Then, for an event find all bookings and use if statement
     * to find out all bookings made within the time period. Finally, add all such bookings into the
     * arraylist named booking, let result arraylist = booking arraylist.
     * @param context Only parameter
     * @return void
     */
    public void execute(Context context) {

        assert (context.getUserState().getCurrentUser() instanceof GovernmentRepresentative) :
                "Current user must be a government representative.";

        List<Booking> bookings = new ArrayList<>();

        for (Event event : context.getEventState().getAllEvents()) {
            //list all valid bookings for individual event
            if (event instanceof TicketedEvent && event.getStatus() == EventStatus.ACTIVE) {
                ListEventBookingsCommand listBookings = new ListEventBookingsCommand(event.getEventNumber());
                listBookings.execute(context);
                List<Booking> eventBookings = listBookings.getResult();

                // only add bookings which occur within timeframe
                for (Booking booking : eventBookings) {
                    LocalDateTime startDate = booking.getEventPerformance().getStartDateTime();
                    LocalDateTime endDate = booking.getEventPerformance().getEndDateTime();
                    if ((startDate.isAfter(intervalStartInclusive) || startDate.isEqual(intervalStartInclusive))
                            && (endDate.isBefore(intervalEndInclusive) || endDate.isEqual(intervalEndInclusive))) {
                        bookings.add(booking);
                        // Note: since we haven't implemented sponsorship acceptance/rejection (as a group of 3),
                        // we are unable to check if the event is sponsored or not, so we omitted it from the requirements
                    }
                }
            }
        }
        result = bookings;
    }
    /**
     * This getter return the list of bookings
     * @return result, an arraylist with all bookings
     */
    public List<Booking> getResult() {
        return result;
    }

}
