package command;

import model.EventType;

public class CreateTicketedEventCommand extends CreateEventCommand {

    public CreateTicketedEventCommand(String title, EventType type) {

        super(title, type);

    }



}
