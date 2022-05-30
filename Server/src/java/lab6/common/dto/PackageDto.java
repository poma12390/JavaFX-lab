package lab6.common.dto;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PackageDto {
    private final CommandResponseDto<? extends Serializable> commandResponseDto;
    private final DatagramSocket ds;

    private int port;

    public int getPort() {
        return port;
    }

    public InetAddress getHost() {
        return host;
    }

    private InetAddress host;

    public PackageDto(CommandResponseDto<? extends Serializable> commandResponseDto, InetAddress host, int port, DatagramSocket ds) {
        this.commandResponseDto = commandResponseDto;
        this.port = port;
        this.host= host;
        this.ds = ds;
    }

    public CommandResponseDto<? extends Serializable> getCommandResponseDto() {
        return commandResponseDto;
    }



    public DatagramSocket getDs() {
        return ds;
    }


}
