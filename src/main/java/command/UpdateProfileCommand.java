package command;

import controller.Context;

public abstract class UpdateProfileCommand implements ICommand {

    protected Boolean successResult;

    public UpdateProfileCommand() {

    }

    protected boolean isProfileUpdateInvalid(Context context,
                                              String oldPassword,
                                              String newEmail) {
        return false;
    }

    public Boolean getResult() {
        return null;
    }

}
