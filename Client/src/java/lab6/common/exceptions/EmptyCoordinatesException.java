package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EmptyCoordinatesException extends InvalidDataException{
    public EmptyCoordinatesException() {
        super("coordinates can't be empty");
        AuthFrame.responses.add("emptyCoordinates");
        RegisterFrame.responses.add("emptyCoordinates");
        MainFrame.errors.add("emptyCoordinates");
    }
}
