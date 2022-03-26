package command;

import controller.Context;
import model.Consumer;
import model.EntertainmentProvider;
import model.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand {

    private Boolean successResult;
    private String oldPassword;;
    private String newOrgName;
    private String newOrgAddress;
    private String newPaymentAccountEmail;
    private String newMainRepName;
    private String newMainRepEmail;
    private String newPassword;
    private List<String> newOtherRepNames;
    private List<String> newOtherRepEmails;

    public UpdateEntertainmentProviderProfileCommand(String oldPassword,
                                                     String newOrgName,
                                                     String newOrgAddress,
                                                     String newPaymentAccountEmail,
                                                     String newMainRepName,
                                                     String newMainRepEmail,
                                                     String newPassword,
                                                     List<String> newOtherRepNames,
                                                     List<String> newOtherRepEmails) {
        super();
        this.oldPassword = oldPassword;
        this.newOrgName = newOrgName;
        this.newOrgAddress = newOrgAddress;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newMainRepName = newMainRepName;
        this.newMainRepEmail = newMainRepEmail;
        this.newPassword = newPassword;
        this.newOtherRepNames = newOtherRepNames;
        this.newOtherRepEmails = newOtherRepEmails;

    }

    @Override
    public void execute(Context context) {
        successResult = false;

        // assert user is consumer
        assert context.getUserState().getCurrentUser() instanceof EntertainmentProvider : "User must be an entertainment provider.";
        EntertainmentProvider currentOrg = (EntertainmentProvider) context.getUserState().getCurrentUser();

        // assert parameters are not null
        assert oldPassword != null : "Old password must not be null.";
        assert newOrgName != null : "New organisation name must not be null";
        assert newOrgAddress != null : "New organisation address must not be null";
        assert newPaymentAccountEmail != null : "New payment account email must not be null.";
        assert newMainRepName != null : "New main representative name must not be null.";
        assert newMainRepEmail != null : "New main representative email must not be null";
        assert newPassword != null : "New password must not be null";
        assert newOtherRepNames != null : "New other representative names must not be null";
        assert newOtherRepEmails != null : "New other representative emails must not be null";

        //assert user is logged in
        assert currentOrg != null : "Current user must be logged in.";

        // assert password correct
        assert !this.isProfileUpdateInvalid(context, oldPassword, newMainRepEmail) : "Password must match old password.";

        // assert email is not taken by other user
        boolean emailVacant = true;
        for (Map.Entry<String, User> user : context.getUserState().getAllUsers().entrySet()) {
            if (user.getValue().getEmail().equals(newMainRepEmail)) {
                emailVacant = false;
                break;
            }
        }
        assert emailVacant : "Email is already taken by other user.";

        // assert no other organisation with same name and address exists
        boolean organisationUnique = true;
        for (Map.Entry<String, User> org : context.getUserState().getAllUsers().entrySet()) {
            if (org.getValue() instanceof EntertainmentProvider) {
                if (newMainRepEmail.equals(((EntertainmentProvider) org.getValue()).getOrgName()) ||
                newOrgAddress.equals(((EntertainmentProvider) org.getValue()).getOrgAddress())) {
                    organisationUnique = false;
                    break;
                }
            }
        }
        assert organisationUnique : "Organisation name is already taken by another organisation.";

        currentOrg.updatePassword(newPassword);
        currentOrg.setOrgName(newOrgName);
        currentOrg.setOrgAddress(newOrgAddress);
        currentOrg.setPaymentAccountEmail(newPaymentAccountEmail);
        currentOrg.setMainRepEmail(newMainRepName);
        currentOrg.setMainRepEmail(newMainRepEmail);
        currentOrg.setOtherRepNames(newOtherRepNames);
        currentOrg.setOtherRepEmails(newOtherRepEmails);
        successResult = true;
    }
}
