package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class WeightFormatException extends InvalidDataException{
    public WeightFormatException() {
        super("wrong weight format");
        AuthFrame.responses.add("invalidWeightFormat");
        RegisterFrame.responses.add("invalidWeightFormat");
        MainFrame.errors.add("invalidWeightFormat");
    }
}
