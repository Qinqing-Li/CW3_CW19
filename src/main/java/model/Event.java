package model;

import java.util.Collection;

public abstract class Event extends Object{
    private long eventNumber;
    private EntertainmentProvider organiser;
    private String title;
    private EventType type;
    private EventStatus status;
    private Collection<EventPerformance> performances;

    protected Event(long eventNumber, EntertainmentProvider organiser,
                       String title, EventType type){
        this.eventNumber = eventNumber;
        this.organiser = organiser;
        this.title = title;
        this.type = type;
        this.status = EventStatus.ACTIVE;
    }

    public void addPerformance(EventPerformance performance){
        this.performances.add(performance);
    }

    public void cancel(){
        this.status = EventStatus.CANCELLED;
    }

    public long getEventNumber(){
        return this.eventNumber;
    }

    public EntertainmentProvider getOrganiser(){
        return this.organiser;
    }

    public EventPerformance getPerformanceByNumber(long performanceNumber) throws NoSuchFieldException {
        if (this.performances == null) {
            throw new NullPointerException("Current event performance is empty");
        }

        for (EventPerformance performance : this.performances){
            if (performance.getPerformanceNumber() == performanceNumber) {
                return performance;
            }
        }
        throw new NoSuchFieldException("Performance number " + performanceNumber +
                " does not exist.");
    }

    public Collection<EventPerformance> getPerformances(){
        return this.performances;
    }

    public EventStatus getStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "";
    }

    public EventType getType(){
        return this.type;
    }
}


