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
        return eventNumber;
    }

    public EntertainmentProvider getOrganiser(){
        return organiser;
    }

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        if (performances == null) {
            throw new NullPointerException("Current event performance is empty");
        }

        for (EventPerformance performance : performances){
            if (performance.getPerformanceNumber() == performanceNumber) {
                return performance;
            }
        }
        return null;
    }

    public Collection<EventPerformance> getPerformances(){
        return performances;
    }

    public EventStatus getStatus(){
        return status;
    }

    public EventType getType(){
        return type;
    }
}


