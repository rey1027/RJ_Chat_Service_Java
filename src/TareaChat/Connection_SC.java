package TareaChat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

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
     * @throws Exception
     */
    public void startConnection() throws Exception{
        connThread.start();
    }

    /**
     * Method so that together with the thread, the message is sent
     * @param data package with message
     * @throws Exception
     */
    public void send(Serializable data) throws Exception{
        connThread.out.writeObject(data);
    }

    /**
     * Method for closing the connection
     * @throws Exception
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

        @Override
        public void run() {
            /**
             * The method is created to prevent exceptions throughout the execution of the socket
             */
            try(ServerSocket server = isServer() ? new ServerSocket(getPort() ) : null;
                Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);
                /**
                 * Loop so that the socket connection remains constant until one of the parts is closed
                 */
                while(true){
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);
                }
            }
            /**
             * where when the exception exists it displays a message that the Connection was closed
             */
            catch (Exception e) {
                onReceiveCallback.accept("Connection closed");
            }
        }
    }
}
