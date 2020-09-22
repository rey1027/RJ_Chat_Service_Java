package tec.tarea.chat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/***
 * Class Chat_RJ
 *
 * Contains the information of the graphic part, create the server and client
 * @author RACHEL
 * @version 1.0
 *
 * ""----the message is sent with the enter key---""
 */

public class Chat_RJ extends Application {
    //Attributes

    /**
     * Boolean value to be able to run the client and the server.
     *
     * It must be parallel execution, change the boolean value to be able to open the two windows.
     * First true and then false./
     * true = server/
     * false = Customer
     *
     */
    private boolean isServer = false;

    /***
     * Area where the conversation will be displayed
     */
    private TextArea chat_messages = new TextArea();

    /**
     * Make the connection with the abstract class to create the server and the client
     */
    private Connection_SC connection = isServer ? createServer() : createClient();

    /**
     *Display TextField, chat_messages, window measurements.
     *The event is created where the communication between the server and the client occurs, printing the message in text area.
     * @return root that is specification for the window
     */
    private Parent createContent(){

        chat_messages.setPrefHeight(500);
        TextField input = new TextField();
        input.setOnAction( event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += input.getText();
            input.clear();

            chat_messages.appendText(message + "\n");

            try{
                connection.send(message);
            }

            catch (Exception e){
                chat_messages.appendText("Failed to send" + "\n");
            }
        });

        VBox root = new VBox(20,chat_messages, input);
        root.setPrefSize(600,600);
        return root;

    }
    @Override
    public void init() throws Exception{
        connection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String title_window;
        title_window = isServer ? "Chat RJ Server" : "Chat RJ Client";
        primaryStage.setTitle(title_window);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    @Override
    public void stop() throws Exception{
        connection.closeConnection();
    }

    /**
     *Method Server creating a new server with a communication port, where the data is then written in the text area
     * @return communication port to use
     */
    private Server createServer(){
        return new Server(55555, data -> Platform.runLater(() -> chat_messages.appendText(data.toString() + "\n")));
    }

    /**
     *Method Client creating a new client with an ip address and a communication port, where the data is then written in the text area
     * @return ip address and communication port
     */
    private Client createClient(){
        return new Client("127.0.0.1",55555, data -> Platform.runLater(() -> chat_messages.appendText(data.toString() + "\n")));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
