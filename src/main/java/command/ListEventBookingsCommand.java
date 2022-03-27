package command;

import controller.Context;
import model.*;

import java.util.List;

public class ListEventBookingsCommand implements ICommand {

    private List<Booking> result;
    private long eventNumber;

    public ListEventBookingsCommand(long eventNumber) {
        this.eventNumber = eventNumber;
    }

    @Override
    public void execute(Context context) {

        assert context.getUserState().getCurrentUser() != null : "Current user must be logged in.";

        Event event = context.getEventState().findEventByNumber(eventNumber);
        assert event != null : "Event number must correspond to an existing event.";

        assert event instanceof TicketedEvent : "Event must be a ticketed event.";
        User currentUser = context.getUserState().getCurrentUser();
        assert (currentUser instanceof EntertainmentProvider ||
                currentUser instanceof GovernmentRepresentative) :
                "Logged in user must be a government representative or entertainment provider.";

        result = context.getBookingState().findBookingsByEventNumber(eventNumber);
    }

    @Override
    public List<Booking> getResult() {
        return result;
    }

}
