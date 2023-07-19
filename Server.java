/***********************************************************************
 * George E. Mitchell
 * 202330 Software Development I CEN-3024C-32552
 * Module 11 | Deployment Assignment
 * 
 * his application uses a client to prompt for a number which
 * is sent to a server which will determine if the number is prime.
 * Once the results are sent back to the client and displayed to the user.
 * 
 * @author George E. Mitchell
 * @since 07/14/2023
***********************************************************************/
package application;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This class handles the processes related to the server.
 * 
 * @author George E. Mitchell
 * @since 07/16/2023
 */
public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
  
        while (true) {
          // Receive number from the client
          int num = inputFromClient.readInt();
  
          // Send prime indicator back to the client
          outputToClient.writeBoolean(isPrime(num));
      
          Platform.runLater(() -> { ta.appendText("Number received from client to check prime number is: " + num + '\n'); });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
  
  /**
   * This method determines if a number is prime.
   * 
   * @param n
   * @return boolean
   */
  private static boolean isPrime(int n) {
	  
	  boolean bool = true;
	  
	  if (n == 1) { bool =  false; }
	  
	  for (int i = 2; i < (n / 2); i++) {
		  
		  if (n % i == 0) {
			  bool = false;
              break;
		  }
	  }
	  
	  return bool;
	  
  }
  
}

