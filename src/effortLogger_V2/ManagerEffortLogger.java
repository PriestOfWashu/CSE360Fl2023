/*
 * File: LoginScreen.java
 * Author: Alifiyz
 * Project: EffortLogger-V2
 * Class: CSE 360 - 
 * Team:: Th21
 */

package effortLogger_V2;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

 

public class ManagerEffortLogger {
	
    private static ComboBox<String> createComboBox(double boxWidth, double boxHeight) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefSize(boxWidth, boxHeight);
        return comboBox;
    }

    public static void start(Stage primaryStage) {
        primaryStage.setTitle("Manager Effort Logger");

        // BorderPane as the primary container
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(30));

        // Title Label
        Label titleLabel = new Label("Manager Effort Logger");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setPadding(new Insets(0, 0, 50, 0)); // 50 units of space below the title
        BorderPane.setAlignment(titleLabel, Pos.CENTER); // Center the title
        borderPane.setTop(titleLabel);

        VBox formContainer = new VBox(50);// Container for the form
        formContainer.setAlignment(Pos.CENTER);

        // Size for ComboBox and TextField for uniformity
        double boxWidth = 300.0;
        double boxHeight = 30.0;  // Height for ComboBox and TextField

        // Date Dropdown
        ComboBox<String> dateDropdown = createComboBox(boxWidth, boxHeight);
        dateDropdown.getItems().addAll("10/24/2023", "10/25/2023");
        dateDropdown.setPromptText("Select Date");

        // Project Dropdown
        ComboBox<String> projectDropdown = createComboBox(boxWidth, boxHeight);
        projectDropdown.getItems().addAll("Project A", "Project B");
        projectDropdown.setPromptText("Select Project");

        // Task Dropdown
        ComboBox<String> taskDropdown = createComboBox(boxWidth, boxHeight);
        taskDropdown.getItems().addAll("Task 1", "Task 2");
        taskDropdown.setPromptText("Select Task");
        // Hours Field
        TextField hoursField = new TextField();
        hoursField.setPrefSize(boxWidth, boxHeight);
        hoursField.setPromptText("Enter hours worked...");
        hoursField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hoursField.setText(newValue.replaceAll("[^\\d]", ""));
                showAlert("Error", "Only numeric values are allowed in 'hours worked' field!");
            }
        });

        // Role Dropdown
        ComboBox<String> roleDropdown = createComboBox(boxWidth, boxHeight);
        roleDropdown.getItems().addAll("Role 1", "Role 2", "Role 3");
        roleDropdown.setPromptText("Select Role");

        // Horizontal container for the three dropdowns on top
        HBox topContainer = new HBox(40);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(dateDropdown, projectDropdown, taskDropdown);

        // Horizontal container for the items below
        HBox bottomContainer = new HBox(20);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().addAll(hoursField, roleDropdown);

        // Add the components to the form container
        formContainer.getChildren().addAll(topContainer, bottomContainer);
        borderPane.setCenter(formContainer);

        // Submit Button
        Button submitButton = new Button("Submit Entry");
        submitButton.setOnAction(e -> {
            if (dateDropdown.getValue() == null || projectDropdown.getValue() == null || taskDropdown.getValue() == null || hoursField.getText().isEmpty() || roleDropdown.getValue() == null) {
                showAlert("Error", "All fields must be filled out!");
            } else {
                // Perform submit action here
                System.out.println("Submitted!");
            }
        });
        VBox bottomBox = new VBox(submitButton);  // Use a VBox to provide padding
        submitButton.setPrefSize(150, 50);
        bottomBox.setPadding(new Insets(40, 0, 0, 0));  // Space above the button
        BorderPane.setAlignment(submitButton, Pos.CENTER);
        bottomBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottomBox);

        // Switch to Defect Logger
        VBox Navi;
        Button SceneSwitch = new Button("Defect Logger");
        SceneSwitch.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					primaryStage.close();
					defectLoggingScreen.start(primaryStage);
				}
			});
        Button EffortViewer = new Button("Effort Viewer");
        EffortViewer.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) {
        		primaryStage.close();
        		FXMLLoader viewer = new FXMLLoader();
        		URL xmlURL = getClass().getResource("ManagerEffortView.fxml");
        		viewer.setLocation(xmlURL);
        		try {
        			Parent root = viewer.load();
        			primaryStage.setScene(new Scene(root));
        			primaryStage.show();
        		} catch(IOException e1) {
        			e1.printStackTrace();
        		}
        	}
        });
        Navi = new VBox(SceneSwitch, EffortViewer);
        BorderPane.setAlignment(Navi, Pos.BOTTOM_CENTER);
        borderPane.setLeft(Navi);
        Scene scene = new Scene(borderPane, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show alert dialog
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
}