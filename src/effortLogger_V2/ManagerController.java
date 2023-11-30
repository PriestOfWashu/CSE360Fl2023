/*
 * File: LoginScreen.java
 * Author: Marisol
 * Project: EffortLogger-V2
 * Class: CSE 360 - 
 * Team:: Th21
 */

package effortLogger_V2;
import java.io.FileInputStream;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ManagerController{
	
	@FXML
	private TextArea displayedInfo1;
    @FXML
    private Button employeeEffort1;
    @FXML
    private ChoiceBox<String> recentChoiceBox1;
    private String[] exampleChoices1 = {"Sprint 1 Week 1", "Sprint 1 Week 2", "Sprint 2 Week 1"};
    @FXML
    private Label titleLabel1;
    @FXML
	private Label UID;
	@FXML
	private Button SwitchView;
	@FXML
	private Button DefectSwitch;
	@FXML
	public void initialize() {
		recentChoiceBox1.getItems().addAll(exampleChoices1);
		recentChoiceBox1.setOnAction(this::getInfo);
		displayedInfo1.setEditable(false);	
		displayedInfo1.setMouseTransparent(true);
		UID.setText(EffortLoggerLogin.UsrToken);
	}
	//The user selection is stored in 'selectedSprint' and is displayed in the text area 'displayedInfo1'
	public void getInfo(ActionEvent event) {
		String selectedSprint = recentChoiceBox1.getValue();
		String dataRead = viewEmployeeRecords(selectedSprint);
		displayedInfo1.setText("Details about " + selectedSprint + "\n" + dataRead); //Displays details about a sprint selected by user
		
	}
	//By clicking the 'Employee Effort' button, the manager can see who worked on the specific task and for how long
	private String userToken;
	private String taskDurationInfo;
	public void getEmployeeEffort(ActionEvent event) {
		String selection = recentChoiceBox1.getValue();
		String dataRead = viewEmployeeRecords(selection);
		displayedInfo1.setText("Details about " + selection + "\n" + dataRead + "\nEmployee: " + userToken + "\n" + taskDurationInfo);
	}	
	//Shows the details of the specific sprint selected
	private String viewEmployeeRecords(String sprintSelection)
	{
		String filePath = EffortLoggerLogin.masterDir + "\\usr\\" + sprintSelection + ".efl"; //the filepath where the user sprints are stored
		String employeeLogInfo = "";
		try {
			Scanner file = new Scanner(new FileInputStream(filePath));
			userToken = file.nextLine();//user token is in the first line
			taskDurationInfo = file.nextLine(); //task duration is in the second line
			while(file.hasNext())
			{
				employeeLogInfo+= file.nextLine() + "\n";
			}
			file.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return employeeLogInfo;
	}
	
	// switch view to the effort logging screen
	@FXML
	private void EffortSwitchHandle() {
		Stage primaryStage = (Stage) SwitchView.getScene().getWindow();
		primaryStage.close();
		ManagerEffortLogger.start(primaryStage);
	}
	
	// switch view to the defect logging screen
	@FXML
	private void DefectSwitchHandle() {
		Stage primaryStage = (Stage) DefectSwitch.getScene().getWindow();
		primaryStage.close();
		defectLoggingScreen.start(primaryStage);
	}
}
