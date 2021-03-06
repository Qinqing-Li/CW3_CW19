package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookEventCommand implements ICommand {

    private Long result;
    private long eventNumber;
    private long performanceNumber;
    private int numTicketsRequested;

    public BookEventCommand(long eventNumber,
                            long performanceNumber,
                            int numTicketsRequested) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    @Override
    public void execute(Context context) {
        result = null;
        // move this error message to a better class if suitable:
        assert context.getUserState().getCurrentUser() != null :
                "Consumer is not logged in.";
        assert context.getUserState().getCurrentUser() instanceof Consumer :
                "Logged in user must be a consumer";
        assert context.getEventState().findEventByNumber(eventNumber) != null :
                "Event number must correspond to an existing event.";
        assert context.getEventState().findEventByNumber(eventNumber) instanceof TicketedEvent :
                "Event must be a ticketed event.";
        assert numTicketsRequested >= 1 : "Number of tickets must be at least 1.";
        TicketedEvent event = (TicketedEvent) context.getEventState().findEventByNumber(eventNumber);
        assert event.getPerformanceByNumber(performanceNumber) != null :
                "Performance number must correspond to an existing performance of the event.";
        EventPerformance performance = event.getPerformanceByNumber(performanceNumber);
        assert performance.getEndDateTime().isAfter(LocalDateTime.now()) :
                "Selected performance has already ended.";
        EntertainmentProviderSystem providerSystem = event.getOrganiser().getProviderSystem();
        assert providerSystem.getNumTicketsLeft(event.getEventNumber(), performanceNumber) >= numTicketsRequested :
                "Not enough tickets available.";
        Consumer booker = (Consumer) context.getUserState().getCurrentUser();

        String buyerAccountEmail = booker.getPaymentAccountEmail();
        String sellerAccountEmail = event.getOrganiser().getPaymentAccountEmail();
        double transactionAmount = event.getOriginalTicketPrice() * numTicketsRequested;
        assert context.getPaymentSystem().processPayment(
                buyerAccountEmail,
                sellerAccountEmail,
                transactionAmount) :
                "Transaction unsuccessful.";

        Booking newBooking = context.getBookingState().createBooking(booker,
                performance,
                numTicketsRequested,
                transactionAmount);

        providerSystem.recordNewBooking(eventNumber,
                performanceNumber,
                newBooking.getBookingNumber(),
                booker.getName(),
                booker.getEmail(),
                numTicketsRequested);

        booker.addBooking(newBooking);

        result = newBooking.getBookingNumber();
    }

    @Override
    public Long getResult() {
        return result;
    }

}
