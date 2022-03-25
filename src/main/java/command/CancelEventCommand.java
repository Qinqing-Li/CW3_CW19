package command;

import controller.Context;
import model.Booking;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;

import java.time.LocalDateTime;
import java.util.List;

import static model.EventStatus.ACTIVE;


public class CancelEventCommand implements ICommand {

    private Boolean result;
    private long eventNumber;
    private String organiserMessage;

    public CancelEventCommand(long eventNumber, String organiserMessage) {
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }

    public void execute(Context context) {
        result = false;

        assert organiserMessage != null : "Organiser message must not be null.";
        assert !organiserMessage.equals("") : "Organiser message must not be blank.";
        assert context.getUserState().getCurrentUser() instanceof EntertainmentProvider :
                "Logged in user must be an entertainment provider";
        EntertainmentProvider entertainmentProvider = (EntertainmentProvider) context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(eventNumber);
        assert event != null : "Event number must correspond to an existing event.";
        assert event.getStatus() == ACTIVE : "Event must be active.";
        assert entertainmentProvider.getEvents().contains(event) : "Logged in user must be the organiser of the event.";

        // assert that no events have started/finished performances
        boolean commencedPerformances = false;
        for (EventPerformance performance : event.getPerformances()) {
            if (performance.getStartDateTime().isBefore(LocalDateTime.now())) {
                commencedPerformances = true;
                break;
            }
        }
        assert !commencedPerformances : "The event must not have any performances that have already started or ended.";

        // refund and cancel all bookings of event
        List<Booking> allBookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
        for (Booking booking : allBookings) {
            String buyerAccountEmail = booking.getBooker().getPaymentAccountEmail();
            String sellerAccountEmail = entertainmentProvider.getPaymentAccountEmail();
            double transactionAmount = booking.getAmountPaid();
            context.getPaymentSystem().processRefund(buyerAccountEmail,
                    sellerAccountEmail,
                    transactionAmount);
            booking.cancelByProvider();
        }
        // TODO how do we use organiserMessage?

        result = true;
    }

    public Boolean getResult() {
        return result;
    }

}
