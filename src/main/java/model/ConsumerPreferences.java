package model;

public class ConsumerPreferences {
    public boolean preferAirFiltration;
    public boolean preferOutdoorsOnly;
    public int preferredMaxCapacity;
    public int preferredMaxVenueSize;
    public boolean preferSocialDistancing;

    public ConsumerPreferences(){
        // default constructor
        this.preferSocialDistancing = false;
        this.preferAirFiltration = false;
        this.preferOutdoorsOnly = false;
        this.preferredMaxCapacity = Integer.MAX_VALUE;
        this.preferredMaxVenueSize = Integer.MAX_VALUE;
    }

}
