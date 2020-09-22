package TareaChat;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/***
 * Class Chat_RJ
 *
 * Contains the information of the graphic part
 * @author RACHEL
 * @version 1.0
 *
 */

public class Chat_RJ extends Application {
    //Attributes

    /**
     * Boolean value to be able to run the client and the server
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
     *Display TextField, chat_messages, window measurements
     * @return root that is specification for the window
     */
    private Parent createContent(){
        chat_messages.setPrefHeight(500);
        TextField input = new TextField();

        VBox root = new VBox(20,chat_messages, input);
        root.setPrefSize(600,600);
        return root;

    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat RJ");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
