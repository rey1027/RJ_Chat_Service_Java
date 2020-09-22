package TareaChat;

import java.io.Serializable;
import java.util.function.Consumer;

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

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port ;
    }
}
