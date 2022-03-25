package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        this.performances = new ArrayList<EventPerformance>();
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

    // added for AddEventPerformanceCommand, but not in javadocs
    public String getTitle() { return title; }
}


