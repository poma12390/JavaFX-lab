package lab6.server;


import lab6.common.Transformer;
import lab6.common.dto.CommandRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientReceiver {
    private static final Logger logger
            = LoggerFactory.getLogger(ClientReceiver.class);
    public static byte arr[] = new byte[65536];
    public static int len = arr.length;
    public static DatagramSocket ds;
    public static DatagramPacket dp;
    int port = 49848;


    void run() {
        try {
            ds = new DatagramSocket(port);
            dp = new DatagramPacket(arr, len);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        requestFromClient();
    }

    public void requestFromClient() {
        Transformer transformer = new Transformer();
        try {
            while (true) {
                ds.receive(dp);
                if (arr[0] == 0) {
                    continue;
                }
                CommandRequestDto<? extends Serializable> dto = (CommandRequestDto<? extends Serializable>) transformer.DeSerialize(arr);
                dto.setPort(dp.getPort());
                dto.setHost(dp.getAddress());
                dto.setDs(ds);
                ServerRunner.queueToProcess.add(dto);
                logger.info("Receive command " + dto.getCommandName());
                //Commands.runCommandFromString(Commands.getWorkersSet(), dto.getCommandName(), dto);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
