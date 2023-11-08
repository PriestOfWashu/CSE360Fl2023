package effortLoggerLogin;

import java.io.IOException;
import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen {
	@FXML
	private TextField EmpID;
	@FXML
	private PasswordField Password;
	@FXML
	private CheckBox MgrLogin;
	@FXML
	private Button LoginButton;

	private String ID;
	private String Pass;
	private boolean Mgr;
	
	// FXML handler for the Login button. Called when the login button is pressed.
	public void Login() throws IOException
	{
		// Blank error message.
		String ErrorMessage = "";
		// Flag for login error. Triggers the error message display.
		boolean errorflag = false;
		// check if the Employee ID AND password fields are blank.
		if(EmpID.getText().isBlank() && Password.getText().isBlank()) {
			ErrorMessage = new String("ID and Password Fields are Blank!");
			errorflag = true;
		}
		// check if the password field is blank.
		else if(Password.getText().isBlank())
		{
			ErrorMessage = new String("Password Field is empty!");
			errorflag = true;
		}
		// check if the employee ID field is blank.
		else if(EmpID.getText().isBlank())
		{
			ErrorMessage = new String("ID Field is empty!");
			errorflag = true;
		}
		else
		{
			// load the values of the FXML fields into the appropriate containers.
			ID = EmpID.getText();
			Pass = Password.getText();
			Mgr = MgrLogin.isSelected();
			// use the password to locate the users' log file.
			// TODO: switch with ID number when file encryption is implemented.
			String file = "C:\\Users\\Public\\EffortLogger\\usr\\" + Pass + ".efl";
			// attempt to load the log file.
			try {
				Scanner infile = new Scanner(new FileInputStream(file));
				infile.useDelimiter("@");
				// verify the file exists.
				if(!infile.hasNext())
				{
					errorflag = true;
					ErrorMessage = "ID/Password Pair not Valid!";
				}
				else
				{
					// Get Employee ID from file
					String inID = infile.next();
					// check if file's ID matches inputed ID
					if(inID.equals(ID)) 
					{
						// check the manager credentials
						boolean inMgr = infile.nextBoolean();
						if(inMgr == Mgr)
						{
							// produce a welcome message.
							String UsrTkn = infile.next();
							EffortLoggerLogin.setToken(UsrTkn);
							Button WlcmBtn = new Button("Ok");
							Label WlcmLbl = new Label("WELCOME!");
							WlcmLbl.setStyle("-fx-font: normal bold 36px 'serif' ");
							BorderPane Wlcm = new BorderPane();
							Wlcm.setPrefHeight(200);
							Wlcm.setPrefWidth(300);
							Wlcm.setTop(WlcmLbl);
							Wlcm.setBottom(WlcmBtn);
							BorderPane.setAlignment(Wlcm.bottomProperty().get(), Pos.CENTER);
							Stage EffortLoggerStage = new Stage();
							if(Mgr)
							{
								Wlcm.setCenter(new Text("Welcome Manager " + UsrTkn));
								// TODO: Call ManagerEffortLogger
								ManagerEffortLogger.start(EffortLoggerStage);
							}
							else
							{
								Wlcm.setCenter(new Text("Welcome User " + UsrTkn));
								// TODO: Call EmployeeEffortLogger
								EmployeeEffortLogger.start(EffortLoggerStage);
							}
							// export the Users' Logger ID Token to the primary class.
							EffortLoggerLogin.setToken(UsrTkn);
							Scene WlcmScene = new Scene(Wlcm);
							Stage WlcmStage = new Stage();
							WlcmBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override public void handle(ActionEvent e) {
									WlcmStage.close();
								}
							});
							WlcmStage.setScene(WlcmScene);
							WlcmStage.show();
							// Close the login window.
							EffortLoggerLogin.closeLogin();
							//Todo: Release control to primary program.
						}
						else
						{
							// set error messages.
							errorflag = true;
							ErrorMessage = "Invalid Manager Credentials!";
						}
					}
					else
					{
						errorflag = true;
						ErrorMessage = "ID/Password Pair not Valid!";
					}
				}
				infile.close();
			} catch (IOException e) {
				errorflag = true;
				ErrorMessage = "ID/Password Pair not Valid!";
			}
		}
		// check if the error flag is set
		if(errorflag)
		{
			Button ErrOk = new Button("OK");
			Label ErrLabel = new Label("ERROR!");
			ErrLabel.setStyle("-fx-font: normal bold 36px 'serif' ");
			BorderPane ERR = new BorderPane();
			ERR.setPrefHeight(200);
			ERR.setPrefWidth(300);
			ERR.setTop(ErrLabel);
			ERR.setBottom(ErrOk);
			BorderPane.setAlignment(ERR.bottomProperty().get(), Pos.CENTER);
			ERR.setCenter(new Text(ErrorMessage));
			Scene ErrScene = new Scene(ERR);
			Stage ErrStage = new Stage();
			ErrOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					ErrStage.close();
				}
			});
			ErrStage.setScene(ErrScene);
			ErrStage.show();
			
		}
	}
}
