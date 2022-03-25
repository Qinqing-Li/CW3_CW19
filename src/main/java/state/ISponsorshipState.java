package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

import java.util.ArrayList;
import java.util.List;

public interface ISponsorshipState {
    List<SponsorshipRequest> sponsorshipRequests = new ArrayList<>();

    int requestNumber = 1;

    SponsorshipRequest addSponsorshipRequest (TicketedEvent event);

    SponsorshipRequest findRequestByNumber (long requestNumber);

    List<SponsorshipRequest> getAllSponsorshipRequests();

    List<SponsorshipRequest> getPendingSponsorshipRequests();
}
