package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class InvalidSalaryException extends InvalidDataException {
    public InvalidSalaryException(){
        super("Bad salary bro");
        AuthFrame.responses.add("invalidSalary");
        RegisterFrame.responses.add("invalidSalary");
        MainFrame.errors.add("invalidSalary");
    }
}
