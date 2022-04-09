package command;

import controller.Context;
import model.EntertainmentProvider;
import model.User;

import java.util.List;
import java.util.Map;

public class RegisterEntertainmentProviderCommand implements ICommand {

    private Object result;
    private String orgName;
    private String orgAddress;
    private String paymentAccountEmail;
    private String mainRepName;
    private String mainRepEmail;
    private String password;
    private List <String> otherRepNames;
    private List<String> otherRepEmails;

    public enum LogStatus{
        REGISTER_ENTERTAINMENT_PROVIDER_SUCCESS,
        USER_REGISTER_FIELDS_CANNOT_BE_NULL,
        USER_REGISTER_EMAIL_ALREADY_REGISTERED,
        USER_REGISTER_ORG_ALREADY_REGISTERED,
        USER_LOGIN_SUCCESS
    }

    public RegisterEntertainmentProviderCommand(String orgName,
                                                 String orgAddress,
                                                 String paymentAccountEmail,
                                                 String mainRepName,
                                                 String mainRepEmail,
                                                 String password,
                                                 List<String> otherRepNames,
                                                 List<String> otherRepEmails) {
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.paymentAccountEmail = paymentAccountEmail;
        this.mainRepName = mainRepName;
        this.mainRepEmail = mainRepEmail;
        this.password = password;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
    }

    @Override
    public void execute(Context context) {
        result = null;
        assert orgName != null : "Organisation name cannot be null.";
        assert orgAddress != null : "Organisation address cannot be null.";
        assert paymentAccountEmail != null : "Payment account email cannot be null.";
        assert mainRepEmail != null : "Main representative email cannot be null.";
        assert mainRepName != null : "Main representative name cannot be null.";
        assert password != null : "Password cannot be null.";
        assert otherRepNames != null : "Other representatives' names cannot be null.";
        assert otherRepEmails != null : "Other representatives' emails cannot be null.";

        Map<String, User> existingUsers = context.getUserState().getAllUsers();
        assert !existingUsers.containsKey(mainRepEmail) : "Main representative email already taken.";

        // assert no other organisation with same name and address exists
        boolean organisationUnique = true;
        for (Map.Entry<String, User> org : existingUsers.entrySet()) {
            if (org.getValue() instanceof EntertainmentProvider) {
                if (mainRepEmail.equals(((EntertainmentProvider) org.getValue()).getOrgName()) ||
                        orgAddress.equals(((EntertainmentProvider) org.getValue()).getOrgAddress())) {
                    organisationUnique = false;
                    break;
                }
            }
        }
        assert organisationUnique : "Organisation name is already taken by another organisation.";

        EntertainmentProvider newEntertainmentProvider = new EntertainmentProvider(
                orgName,
                orgAddress,
                paymentAccountEmail,
                mainRepName,
                mainRepEmail,
                password,
                otherRepNames,
                otherRepEmails
        );

        context.getUserState().addUser(newEntertainmentProvider);
        context.getUserState().setCurrentUser(newEntertainmentProvider);
        result = newEntertainmentProvider;
    }

    @Override
    public Object getResult() {
        return result;
    }
}
