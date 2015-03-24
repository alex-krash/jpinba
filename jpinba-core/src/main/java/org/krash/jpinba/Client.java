package org.krash.jpinba;

import org.krash.jpinba.data.JPinbaRequest;
import org.krash.jpinba.request.PinbaRequest;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Client, operating send of serialized data to pinba
 * @author krash
 */
public class Client implements Closeable {

    public static final int DEFAULT_PORT = 30002;

    private final String host;
    private final int port;
    private InetAddress address;
    DatagramSocket clientSocket;

    /**
     * @param host address of host, Pinba listens to
     * @param port binded port
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Client(InetAddress address, int port)
    {
        this(address.getHostName(), port);
        this.address = address;
    }

    public Client(InetAddress address) {
        this(address.getHostName());
        this.address = address;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public Client(String host) {
        this(host, DEFAULT_PORT);
    }

    /**
     * Send single request
     *
     * @param request data to send
     */
    public synchronized void send(JPinbaRequest request) throws IOException {
        PinbaRequest.RequestBody.Builder builder = PinbaRequest.RequestBody.newBuilder();
        request.initBuilder(builder);

        if(null == clientSocket) {
            clientSocket = new DatagramSocket();
        }
        PinbaRequest.RequestBody b = builder.build();
        byte aSendData[] = b.toByteArray();

        if(null == address) {
            address = InetAddress.getByName(host);
        }
        DatagramPacket aSendPacket = new DatagramPacket( aSendData, aSendData.length, address, port);
        clientSocket.send(aSendPacket);
    }

    @Override
    public void close() throws IOException {
        if(null != clientSocket) {
            clientSocket.close();
            clientSocket = null;
        }
    }
}
