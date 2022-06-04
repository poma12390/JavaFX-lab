package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class CoordinatesFormatException extends InvalidDataException{
    public CoordinatesFormatException() {
        super("Wrong x or y format");
        AuthFrame.responses.add("invalidCoordinatesFormat");
        RegisterFrame.responses.add("invalidCoordinatesFormat");
        MainFrame.errors.add("invalidCoordinatesFormat");
    }
}
