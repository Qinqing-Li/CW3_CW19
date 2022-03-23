package model;

public class TicketedEvent extends Event{
    private double ticketPrice;
    private int numTickets;
    private SponsorshipRequest sponsorshipRequest;

    public TicketedEvent(long eventNumber, EntertainmentProvider organiser,
                          String title, EventType type, double ticketPrice, int numTickets){
        super(eventNumber,organiser,title,type);
        this.ticketPrice = ticketPrice;
        this.numTickets = numTickets;
    }

    public double getDiscountedTicketPrice(){
        if (sponsorshipRequest == null){
            return this.ticketPrice;
        }else if (sponsorshipRequest.getStatus() != SponsorshipStatus.ACCEPTED){
            return this.ticketPrice;
        }
        return sponsorshipRequest.getSponsoredPricePercent() * ticketPrice / 100;
    }

    public int getNumTickets(){
        return numTickets;
    }

    public double getOriginalTicketPrice(){
        return ticketPrice;
    }

    public String getSponsorAccountEmail(){
        if (sponsorshipRequest == null){
            return null;
        }else if (sponsorshipRequest.getStatus() != SponsorshipStatus.ACCEPTED){
            return null;
        }
        return sponsorshipRequest.getSponsorAccountEmail();
    }

    public boolean isSponsored(){
        if (sponsorshipRequest == null) {
            return false;
        }
        return sponsorshipRequest.getStatus() == SponsorshipStatus.ACCEPTED;
    }

    public void	setSponsorshipRequest(SponsorshipRequest sponsorshipRequest){
        this.sponsorshipRequest = sponsorshipRequest;
    }

    @Override
    public String toString(){
        return "Ticket price " + ticketPrice + " num of tickets " + numTickets;
    }
}
