package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

/**
 * thrown when script call loops
 */
public class RecursiveScriptExecuteException extends CommandException{
    public RecursiveScriptExecuteException(){
        super("recursive script execute attempt");
        AuthFrame.responses.add("recursiveScript");
        RegisterFrame.responses.add("recursiveScript");
        MainFrame.errors.add("recursiveScript");
    }
}
