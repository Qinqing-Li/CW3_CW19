package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
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

            boolean titleOK = true;
            for (Event event : ((EntertainmentProvider) context.getUserState().getCurrentUser()).getEvents()) {
                if (event.getTitle().equals(title)) {
                    titleOK = false;
                }
            }

            if (titleOK) {
                EntertainmentProvider thisProvider = (EntertainmentProvider) context.getUserState().getCurrentUser();
                TicketedEvent newEvent = context.getEventState().createTicketedEvent(thisProvider,
                        title,
                        type,
                        ticketPrice,
                        numTickets);

                thisProvider.addEvent(newEvent);

                this.eventNumberResult = newEvent.getEventNumber();
                thisProvider.getProviderSystem().recordNewEvent(newEvent.getEventNumber(), title, numTickets);
            } else {
                this.eventNumberResult = null;
            }
        }
        else {
            this.eventNumberResult = null;
        }
    }
}
