package tec.tarea.chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 * Contains creation and connection of the socket to be executed in the Chat RJ
 * @author RACHEL_PEREIRA
 * @version 1.0
 *
 */
public abstract class Connection_SC {
    /**
     * The NetConnectThread thread class is declared to be able to define it in the other methods
     */
    private NetConnectThread connThread = new NetConnectThread();
    /**
     * OnReceiveCallback is declared as serializable for message behavior
     */
    private Consumer<Serializable> onReceiveCallback;

    /**
     * A public method called as the abstract one is created where the behavior is defined
     * The daemon method is applied to the method thread to change the Thread Properties of the daemon.
     * @param onReceiveCallback message behavior
     */
    public Connection_SC(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connThread.setDaemon(true);
    }

    /**
     *Method for starting the thread
     * @throws Exception connection closed
     */
    public void startConnection() throws Exception{
        connThread.start();
    }

    /**
     * Method so that together with the thread, the message is sent
     * @param data package with message
     * @throws Exception failed send
     */
    public void send(Serializable data) throws Exception{
        connThread.out.writeObject(data);
    }

    /**
     * Method for closing the connection
     * @throws Exception active connection
     */
    public void closeConnection() throws Exception{
        connThread.socket.close();
    }

    /**
     * Different protected methods are created to define the server, the port and the IP
     * @return port value, ip address and boolean value to be able to define the client or server
     */
    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    /**
     * A child class called Thread is created
     */

    private class NetConnectThread extends Thread {
        /**
         * Create the variable local
         */
        private Socket socket ;
        private ObjectOutputStream out;

        /**
         * Run de ServerSocket, sending data through a loop that is active for constant communication until the connection is closed
         */
        @Override
        public void run() {
            try(ServerSocket server = isServer() ? new ServerSocket(getPort() ) : null;
                Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {


                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while(true){
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);
                }
            }

            catch (Exception e) {
                onReceiveCallback.accept("Connection closed");
            }
        }
    }
}
