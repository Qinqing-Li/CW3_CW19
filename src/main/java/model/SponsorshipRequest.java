package model;

public class SponsorshipRequest {
    private long requestNumber;
    private TicketedEvent event;
    private SponsorshipStatus status;
    private int sponsoredPricePercent;
    private String sponsorAccountEmail;

    public SponsorshipRequest (long requestNumber, TicketedEvent event){
        this.requestNumber = requestNumber;
        this.event = event;
        this.status = SponsorshipStatus.PENDING;
    }

    public void	accept (int percent, String sponsorAccountEmail){
        this.status = SponsorshipStatus.ACCEPTED;
        this.sponsoredPricePercent = percent;
        this.sponsorAccountEmail = sponsorAccountEmail;
    }

    public TicketedEvent getEvent(){
        return event;
    }

    public long	getRequestNumber(){
        return requestNumber;
    }

    public String getSponsorAccountEmail(){
        if (status == SponsorshipStatus.ACCEPTED){
            return sponsorAccountEmail;
        }
        return null;
    }
    
    public Integer getSponsoredPricePercent(){
        if (status == SponsorshipStatus.ACCEPTED){
            return sponsoredPricePercent;
        }
        return null;
    }
    
    public SponsorshipStatus getStatus(){
        return status;
    }
    
    public void reject(){
        status = SponsorshipStatus.REJECTED;
    }
}
