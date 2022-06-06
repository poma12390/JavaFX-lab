package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EmptyPositionException extends InvalidDataException{
    public EmptyPositionException() {
        super("Position cannot be empty");
        MainFrame.errors.add("emptyPosition");
    }
}
