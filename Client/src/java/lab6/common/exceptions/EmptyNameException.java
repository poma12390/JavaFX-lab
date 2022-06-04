package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EmptyNameException extends InvalidDataException{
    public EmptyNameException() {
        super("name can't be empty");
        AuthFrame.responses.add("emptyName");
        RegisterFrame.responses.add("emptyName");
        MainFrame.errors.add("emptyName");
    }
}
