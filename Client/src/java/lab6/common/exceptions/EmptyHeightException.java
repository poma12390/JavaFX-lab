package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EmptyHeightException extends InvalidDataException{
    public EmptyHeightException() {
        super("height can't be empty");
        AuthFrame.responses.add("emptyHeight");
        RegisterFrame.responses.add("emptyHeight");
        MainFrame.errors.add("emptyHeight");
    }
}
