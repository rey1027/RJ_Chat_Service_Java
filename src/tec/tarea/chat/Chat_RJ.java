package tec.tarea.chat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Scanner;

/***
 *
 * Contains the information of the graphic part, create the server and client
 * @author RACHEL_PEREIRA
 * @version 1.0
 *
 */

public class Chat_RJ extends Application implements EventHandler<ActionEvent> {
    //Attributes

    /**
     *Create a new element of the Scanner class that will allow us to read in the console
     */
    private Scanner read = new Scanner(System.in);

    /**
     *Variable that contains the value that is obtained in the console
     */
    private int port_console = read.nextInt();


    /**
     * Boolean value to be able to run the client and the server.
     *
     * It must be parallel execution, change the boolean value to be able to open the two windows.
     * First true and then
     * true = server/
     * false = Client
     *
     */
    private boolean isServer = false;

    /**
     *Label to put text inside the window
     */
    private Label label = new Label("Service Chat RJ");

    /***
     * Area where the conversation will be displayed
     */
    private TextArea chat_messages = new TextArea();

    /**
     * Make the connection with the abstract class to create the server and the client
     */
    private Connection_SC connection = isServer ? createServer() : createClient();

    /**
     *Button for send messages
     */
    private Button button_Send = new Button("SEND");

    /**
     *Space to write your message
     */
    private TextField input = new TextField();

    /**
     *Display window measurements.
     *The event is created where the communication between the server and the client occurs, printing the message in text area.
     * @return root that is specification for the window
     */
    private Parent createContent(){

        chat_messages.setPrefHeight(375);
        label.setAlignment( Pos.TOP_CENTER );
        label.setContentDisplay(ContentDisplay.TOP);
        label.setFont(new Font("Agency FB",25));
        button_Send.setFont(new Font("Cambria",25));
        button_Send.setOnAction(this);
        VBox root = new VBox(20, label,chat_messages, input, button_Send);
        root.setPrefSize(450,550);
        return root;

    }

    /**
     * Initiates the connection between the server and the client
     * @throws Exception Closed Connection
     */
    @Override
    public void init() throws Exception{
        connection.startConnection();
    }

    /**
     *Initialize the Chat window with the different objects added
     * @param primaryStage Chat window
     * @throws Exception ERROR
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        String title_window;
        title_window = isServer ? "Chat RJ Server" : "Chat RJ Client";
        primaryStage.setTitle(title_window);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     *Close the connection between the server and the client
     * @throws Exception Active connection
     */
    @Override
    public void stop() throws Exception{
        connection.closeConnection();
    }

    /**
     *Method Server creating a new server with a communication port, where the data is then written in the text area
     * @return communication port to use
     */
    private Server createServer(){
        return new Server(port_console, data -> Platform.runLater(() -> chat_messages.appendText(data.toString() + "\n")));
    }

    /**
     *Method Client creating a new client with an ip address and a communication port, where the data is then written in the text area
     * @return ip address and communication port
     */
    private Client createClient(){
        return new Client("127.0.0.1",port_console, data -> Platform.runLater(() -> chat_messages.appendText(data.toString() + "\n")));
    }

    /**
     *Show the actions corresponding to the console
     * @param args stores values
     */
    public static void main(String[] args) {
        System.out.println("NOTA IMPORTANTE: Primero el isServer en true e ingresar el puerto y luego en false realizando el mismo paso"+"***Mismo Puerto***");
        System.out.println("Luego debe darle enter primero el de true y luego el false para que funcione correctamente");
        System.out.println("Server =true y Client =false ");
        System.out.println("\n");
        System.out.println("Ingrese el puerto que desea utilizar:");
        launch(args);
    }

    /**
     *Send message button functionality
     * @param event send message
     */
    @Override
    public void handle(ActionEvent event) {
        if ((Button) event.getSource() == button_Send){
            String message = isServer ? "Server:" : "Client:";
            message += input.getText();
            input.clear();

            chat_messages.appendText(message + "\n");

            try{
                connection.send(message);
            }
            catch (Exception e){
                chat_messages.appendText("Failed to send" + "\n");
            }
        }
    }
}
