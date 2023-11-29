package effortLogger_V2;
import java.io.FileInputStream;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmployeeController {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextArea displayedInfo;
	@FXML
	private ChoiceBox<String> recentChoiceBox;
	private String[] exampleChoices = {"Sprint 1 Week 1", "Sprint 1 Week 2", "Sprint 2 Week 1"};
	@FXML
	private Label titleLabel;
	@FXML
	private Label UID;
	@FXML
	private Button SwitchView;
	@FXML
	private Button DefectSwitch;
	@FXML
	public void initialize(){
		recentChoiceBox.getItems().addAll(exampleChoices); 
		recentChoiceBox.setOnAction(this::getInfo);
		displayedInfo.setEditable(false);	
		displayedInfo.setMouseTransparent(true);
		UID.setText(EffortLoggerLogin.UsrToken);
	}
	//The details of a sprint is displayed in the text area
	public void getInfo(ActionEvent event) {
		String selectedSprint = recentChoiceBox.getValue();
		String dataRead = effortLogInformation(selectedSprint);
		displayedInfo.setText("Details about " + selectedSprint + "\n" + dataRead); //Displays details about a sprint selected by user
		
	}
	//The employee selects a sprint and can view the details of that sprint
	private String effortLogInformation(String sprintSelection)
	{
		String filePath = EffortLoggerLogin.masterDir + "\\usr\\" + sprintSelection + ".efl"; //the file path of the user's sprints
		String data = "";
		try {
			Scanner file = new Scanner(new FileInputStream(filePath));
			file.nextLine(); //First line of the file contains user token
			file.nextLine(); //Second line of the file contains the duration of the specific task
			while(file.hasNext())
			{
				data += file.nextLine()+ "\n";
			}
			file.close();
		}
		catch(Exception e) {
			e.getStackTrace();
		}
		return data; //returns the selected sprint details
	}
	
	@FXML
	private void EffortSwitchHandle() {
		Stage primaryStage = (Stage) SwitchView.getScene().getWindow();
		primaryStage.close();
		EmployeeEffortLogger.start(primaryStage);
	}
	
	@FXML
	private void DefectSwitchHandle() {
		Stage primaryStage = (Stage) DefectSwitch.getScene().getWindow();
		primaryStage.close();
		defectLoggingScreen.start(primaryStage);
	}
}
