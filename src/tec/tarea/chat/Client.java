package tec.tarea.chat;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends Connection_SC {
    /**
     * Define variable ip as String and port as Integer
     */
    private String ip;
    private int port;

    /**
     * Client method receives the ip and port that is in the parent class
     * @param ip ip direction for the server
     * @param port port for communication
     * @param onReceiveCallback data behavior
     */
    public Client(String ip , int port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
