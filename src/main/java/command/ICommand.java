package command;

import controller.Context;

public interface ICommand {

    public void execute(Context context);

    public Object getResult() ;

}
