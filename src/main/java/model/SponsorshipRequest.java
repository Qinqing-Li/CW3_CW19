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
        return this.event;
    }

    public long	getRequestNumber(){
        return this.requestNumber;
    }

    public String getSponsorAccountEmail(){
        if (this.status == SponsorshipStatus.ACCEPTED){
            return this.sponsorAccountEmail;
        }
        return null;
    }
    
    public Integer getSponsoredPricePercent(){
        if (this.status == SponsorshipStatus.ACCEPTED){
            return this.sponsoredPricePercent;
        }
        return null;
    }
    
    public SponsorshipStatus getStatus(){
        return this.status;
    }
    
    public void reject(){
        this.status = SponsorshipStatus.REJECTED;
    }
}
