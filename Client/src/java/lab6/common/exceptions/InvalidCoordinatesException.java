package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

/**
 * thrown when unable to create file
 */
public class InvalidCoordinatesException extends InvalidDataException{
    public InvalidCoordinatesException(){
        super("bad x or y");
        AuthFrame.responses.add("invalidCoordinates");
        RegisterFrame.responses.add("invalidCoordinates");
        MainFrame.errors.add("invalidCoordinates");
    }
}
