package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventType;
import model.TicketedEvent;

public class CreateTicketedEventCommand extends CreateEventCommand {

    private int numTickets;
    private double ticketPrice;
    private boolean requestSponsorship;

    public CreateTicketedEventCommand(String title,
                                       EventType type,
                                       int numTickets,
                                       double ticketPrice,
                                       boolean requestSponsorship) {
        super(title, type);
        this.numTickets = numTickets;
        this.ticketPrice = ticketPrice;
        this.requestSponsorship = requestSponsorship;
    }


    @Override
    public void execute(Context context) {
        if (this.isUserAllowedToCreateEvent(context)) {
            TicketedEvent newEvent = context.getEventState().createTicketedEvent(
                    (EntertainmentProvider) context.getUserState().getCurrentUser(),
                    title,
                    type,
                    ticketPrice,
                    numTickets);
            this.eventNumberResult = newEvent.getEventNumber();
            // this is where you would implement sponsorship request
        }
        else {
            this.eventNumberResult = null;
        }
    }
}
