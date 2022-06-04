package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class ServerNotResponseException extends RuntimeException{
    public ServerNotResponseException() {
        AuthFrame.responses.add("waitingConnection");
        RegisterFrame.responses.add("waitingConnection");
        MainFrame.errors.add("waitingConnection");
    }
}
