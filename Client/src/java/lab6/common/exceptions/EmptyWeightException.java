package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EmptyWeightException extends InvalidDataException{
    public EmptyWeightException() {
        super("weight can't be empty");
        AuthFrame.responses.add("emptyWeight");
        RegisterFrame.responses.add("emptyWeight");
        MainFrame.errors.add("emptyWeight");
    }
}
