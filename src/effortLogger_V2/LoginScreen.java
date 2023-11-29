package effortLogger_V2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;
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
	private int loginFailCount = 0;
	private static String dir = "C:\\Users\\Public\\EffortLogger\\usr\\";
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
			String file = EffortLoggerLogin.masterDir + "\\usr\\" + ID + ".efl";
			// attempt to load the log file.
			try {
				// set up the file reader and encryption tool.
				FileInputStream eflFile = new FileInputStream(file);
				Scanner infile = new Scanner(eflFile);
				StrongTextEncryptor decrypt = new StrongTextEncryptor();
				decrypt.setPassword(Pass);
				EffortLoggerLogin.tmpFile = File.createTempFile("tmp-", "eflt");
				EffortLoggerLogin.tmpFile.deleteOnExit();
				FileOutputStream tmp = new FileOutputStream(EffortLoggerLogin.tmpFile);
				try
				{
					// attempt to decrypt the file.
					String in = infile.next();
					String hold = decrypt.decrypt(in);
					tmp.write(hold.getBytes());
					infile.close();
					eflFile.close();
					tmp.close();
					// Open the temporary file for use
					infile = new Scanner(new FileInputStream(EffortLoggerLogin.tmpFile));
					infile.useDelimiter("@");
					// verify the file exists.
					if(!infile.hasNext())
					{
						errorflag = true;
						ErrorMessage = "ID/Password Pair not Valid!";
						loginFailCount++;
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
									EffortLoggerLogin.mgr = true;
									ManagerEffortLogger.start(EffortLoggerStage);
								}
								else
								{
									Wlcm.setCenter(new Text("Welcome User " + UsrTkn));
									// TODO: Call EmployeeEffortLogger
									EffortLoggerLogin.mgr = false;
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
								// TODO: Release control to primary program.
							}
							else
							{
								// set error messages.
								errorflag = true;
								ErrorMessage = "Invalid Manager Credentials!";
								loginFailCount++;
							}
						}
						else
						{
							// If the user file is not valid:
							errorflag = true;
							ErrorMessage = "ID/Password Pair not Valid!";
							loginFailCount++;
						}
					}
					infile.close();
				}
				// watch for a failed decryption error
				catch(EncryptionOperationNotPossibleException e)
				{
					errorflag = true;
					ErrorMessage = "ID/Password Pair not Valid!";
					loginFailCount++;
					infile.close();
					eflFile.close();
					tmp.close();
				}
			}
			// watch of an IO exception
			catch (IOException e) {
				errorflag = true;
				ErrorMessage = "ID/Password Pair not Valid!";
			}
		}
		// check if the error flag is set
		if(errorflag)
		{
			// Produce an error message.
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
		// check if the user have failed to log in multiple times.
		// Currently under debugging	
		if(loginFailCount >= 3)
		{
			lockout(ID);
		}
	}
	
	// not commented due to errors, will be commented once problems are fixed.
	@SuppressWarnings("unused")
	private void lockout(String ID) throws IOException
	{
		String filename = dir + ID + ".efl";
		File chk = new File(filename);
		if(chk.exists())
		{
			Path src = Paths.get(filename);
			Files.move(src, src.resolveSibling(ID + "X.efl"));
			Button ErrOk = new Button("OK");
			Label ErrLabel = new Label("LOCKOUT!");
			ErrLabel.setStyle("-fx-font: normal bold 36px 'serif' ");
			BorderPane ERR = new BorderPane();
			ERR.setPrefHeight(200);
			ERR.setPrefWidth(300);
			ERR.setTop(ErrLabel);
			ERR.setBottom(ErrOk);
			BorderPane.setAlignment(ERR.bottomProperty().get(), Pos.CENTER);
			ERR.setCenter(new Text("This Accouint has been locked out. Please contact your HR Representative."));
			Scene ErrScene = new Scene(ERR);
			Stage ErrStage = new Stage();
			ErrOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					ErrStage.close();
				}
			});
			ErrStage.setScene(ErrScene);
			ErrStage.show();
			loginFailCount = 0;
		}
		return;
	}
}
