package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
import model.EventType;
import model.NonTicketedEvent;

public class CreateNonTicketedEventCommand extends CreateEventCommand {

    public CreateNonTicketedEventCommand(String title, EventType type) {

        super(title, type);

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
                System.out.println("Title OK");
                EntertainmentProvider thisProvider = (EntertainmentProvider) context.getUserState().getCurrentUser();
                NonTicketedEvent newEvent = context.getEventState().createNonTicketedEvent(thisProvider, title, type);
                thisProvider.addEvent(newEvent);
                this.eventNumberResult = newEvent.getEventNumber();
            } else { this.eventNumberResult = null;
                System.out.println("Title not OK");}
        } else {
            this.eventNumberResult = null;
            System.out.println("User not allowed to create");
        }
    }

}
