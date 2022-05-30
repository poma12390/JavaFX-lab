package lab6.client;


import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerReceiver {

    public static SocketAddress receiveFromServer(DatagramChannel dc, ByteBuffer buf, InetAddress host, int port) {
        try {
            SocketAddress addr;
            addr = dc.receive(buf);
            return addr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleep(int mils){
        try {
            Thread.sleep(mils);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
