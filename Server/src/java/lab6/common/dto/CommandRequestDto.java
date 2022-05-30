package lab6.common.dto;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CommandRequestDto<T extends Serializable> implements Serializable {
    private String commandName;
    private T commandArgs;

    private String login;
    private String password;

    private DatagramSocket ds;

    private int port;
    private InetAddress host;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getHost() {
        return host;
    }

    public void setHost(InetAddress host) {
        this.host = host;
    }


    public DatagramSocket getDs() {
        return ds;
    }

    public void setDs(DatagramSocket ds) {
        this.ds = ds;
    }


    public CommandRequestDto(String commandName, T commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public T getCommandArgs() {
        return commandArgs;
    }

    public void setCommandArgs(T commandArgs) {
        this.commandArgs = commandArgs;
    }
}
