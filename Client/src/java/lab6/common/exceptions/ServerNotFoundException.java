package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class ServerNotFoundException extends RuntimeException{
    public ServerNotFoundException(){
        super("Server not found");
        AuthFrame.responses.add("noConnection");
        RegisterFrame.responses.add("noConnection");
        MainFrame.errors.add("noConnection");
    }
}
