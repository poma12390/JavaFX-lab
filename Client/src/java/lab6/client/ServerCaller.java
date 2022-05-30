package lab6.client;


import lab6.common.Transformer;
import lab6.common.dto.CommandResponseDto;
import lab6.common.exceptions.AuthorizationException;
import lab6.common.exceptions.ServerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerCaller {
    private static final Logger logger
            = LoggerFactory.getLogger(ServerCaller.class);
    DatagramChannel dc;
    ByteBuffer buf;
    InetAddress host;
    int port = 49848;
    SocketAddress addr;
    SocketAddress addr1;

    public byte[] sendToServer(byte[] arr) {


        try {
            byte[] ret = new byte[65536];
            byte[] recive = new byte[4096];
            try {
                dc = DatagramChannel.open();
                host = InetAddress.getLocalHost();

                buf = ByteBuffer.wrap(arr);
            } catch (IOException e) {
                logger.error("NO HOST");
                throw new RuntimeException(e);
            }
            addr = new InetSocketAddress(host, port);
            addr1 = new InetSocketAddress(host, port);
            for (int i = 0; i < 5; i++) {
                try {
                    buf = ByteBuffer.wrap(arr);
                    dc.configureBlocking(false);
                    logger.info(buf.array().length + " send " + host + " ");
                    dc.send(buf, addr1);
                    buf = ByteBuffer.wrap(ret);
                    ServerReceiver.sleep(1000);
                    addr = ServerReceiver.receiveFromServer(dc, buf, host, port);
                    //addr = dc.receive(buf);
                    Transformer transformer = new Transformer();


                    if (ret[0]!=0) {
                        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf.array());
                        if (response.getResponse()!=null && response.getResponse().equals("you should be authorized")) {
                            throw new AuthorizationException();
                        }
                        return buf.array();
                    }
                    if (i < 4) {
                        logger.warn("Trying to connect to server");
                    } else throw new ServerNotFoundException();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return recive;
        } finally {
            try {
                dc.close();
            } catch (IOException ignored) {
            }
        }

    }

}