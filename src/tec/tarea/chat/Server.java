package tec.tarea.chat;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * Contains what is necessary for the creation of the server
 * @author RACHEL_PEREIRA
 * @version 1.0
 *
 */
public class Server extends Connection_SC {
    /**
     * Variable port as integer
     */
    private int port;

    /**
     * Server method receives the port that is in the parent class
     * @param port port for communication
     * @param onReceiveCallback data behavior
     */
    public Server(int port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
    }

    /**
     *
     * @return referring to the server
     */
    @Override
    protected boolean isServer() {
        return true;
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
        return port ;
    }
}
