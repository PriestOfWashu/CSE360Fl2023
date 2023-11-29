package effortLogger_V2;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;
 
public class EffortLoggerLogin extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    // create two global variables to allow program to remotely access specific
    // methods or data.
    static String UsrToken;
    private static Stage Login;
    public static File tmpFile;
    public static boolean mgr;
    public static String masterDir;
    public void start(Stage primaryStage) {
    	// Create an FXML handler for the created scene.
        FXMLLoader login = new FXMLLoader();
        // create a URL pointer to the FXML file.
        URL xmlUrl = getClass().getResource("Login Screen.fxml");
        // load the RUL into the FXML handler.
        login.setLocation(xmlUrl);
        // initialize the GUI stage.
        Login = new Stage();
        /*
         * Load the GUI details into the stage and show the resulting gui.
         * */
        try {
        	Parent root = login.load();
        	Login.setScene(new Scene(root));
        	Login.show();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        // set master directory
        masterDir = System.getProperty("user.dir");
    }
    /*
     * Sets the Global User Token for other modules to use.
     */
    public static void setToken(String tkn)
    {
    	UsrToken = tkn;
    }
    
    /*
     * Closes out the login screen by closing the Login Stage.
     * Currently also prints the User token.
     */
    public static void closeLogin() {
    	Login.close();
    	System.out.println(UsrToken);
    }
}