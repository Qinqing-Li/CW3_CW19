package model;

public class NonTicketedEvent extends Event{
    public NonTicketedEvent (long eventNumber, EntertainmentProvider organiser,
                             String title, EventType type){
        super(eventNumber,organiser,title,type);
    }

    @Override
    public String toString(){
        return "Event number " + getEventNumber() + "\norganiser " + getOrganiser()
                + "\n type " + getType();
    }
}
