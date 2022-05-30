package lab6.server;

import lab6.common.Transformer;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientCaller {
    private static final Logger logger
            = LoggerFactory.getLogger(ClientCaller.class);
    InetAddress host;
    int port;
    byte[] arr;
    int len;


    public  void send(PackageDto packageDto){
        CommandResponseDto<? extends Serializable> commandResponseDto = packageDto.getCommandResponseDto();
        DatagramSocket ds = packageDto.getDs();

        arr = Transformer.serialize(commandResponseDto);
        len = arr.length;
        port = packageDto.getPort();
        host = packageDto.getHost();
        DatagramPacket dp1 = new DatagramPacket(arr,len,host,port);
        try {
            logger.info("send  " + len + " bytes to" + host + " " + port);
            ds.send(dp1);
        } catch (IOException ignored) {}
    }

}
