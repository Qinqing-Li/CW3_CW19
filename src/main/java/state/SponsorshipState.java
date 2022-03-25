package state;

import model.SponsorshipRequest;
import model.SponsorshipStatus;
import model.TicketedEvent;

import java.util.ArrayList;
import java.util.List;

public class SponsorshipState implements ISponsorshipState{

    private List<SponsorshipRequest> sponsorshipRequests;
    private int requestNumber;

    public SponsorshipState(){
        sponsorshipRequests = new ArrayList<>();
        requestNumber = 1;
    }

    public SponsorshipState (ISponsorshipState other){
        this.sponsorshipRequests = other.sponsorshipRequests;
        requestNumber = other.requestNumber;
    }

    @Override
    public SponsorshipRequest addSponsorshipRequest(TicketedEvent event) {
        SponsorshipRequest newRequest = new SponsorshipRequest(requestNumber, event);
        sponsorshipRequests.add(newRequest);
        requestNumber++;
        return newRequest;
    }

    @Override
    public SponsorshipRequest findRequestByNumber(long requestNumber) {
        for (SponsorshipRequest sponsorshipRequest : sponsorshipRequests){
            if (sponsorshipRequest.getRequestNumber() == requestNumber){
                return sponsorshipRequest;
            }
        }
        return null;
    }

    @Override
    public List<SponsorshipRequest> getAllSponsorshipRequests() {
        return sponsorshipRequests;
    }

    @Override
    public List<SponsorshipRequest> getPendingSponsorshipRequests() {
        List<SponsorshipRequest> pendingRequests = new ArrayList<>();
        for (SponsorshipRequest sponsorshipRequest : sponsorshipRequests){
            if (sponsorshipRequest.getStatus() == SponsorshipStatus.PENDING){
                pendingRequests.add(sponsorshipRequest);
            }
        }
        return pendingRequests;
    }
}
