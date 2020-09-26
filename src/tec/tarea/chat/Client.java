package tec.tarea.chat;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * Contains what is necessary for the creation of the client
 * @author RACHEL_PEREIRA
 * @version 1.0
 *
 */

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

    /**
     *
     * @return referring to the client
     */
    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return null;
    }

    /**
     *
     * @return port to use
     */
    @Override
    protected int getPort() {
        return port;
    }
}
