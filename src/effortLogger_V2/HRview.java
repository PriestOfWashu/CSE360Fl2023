package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HRview extends Application {
    private List<String> effortLogs = new ArrayList<>();
    private List<String> auditTrail = new ArrayList<>();
    private TextArea logTextArea;
    private Button editLogsButton;
    private Button viewLogsButton;
    private ComboBox<String> userComboBox;
    private TextField actionField;
    private TextField timeSpentField;
    private Label nameLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Effort Logger");

        // Create a VBox to hold UI elements
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Create text fields for action and time spent
        actionField = new TextField();
        actionField.setPromptText("Enter action");

        timeSpentField = new TextField();
        timeSpentField.setPromptText("Enter time spent");

        // Create a Button to log effort
        Button logButton = new Button("Log Effort");
        logButton.setOnAction(event -> logEffort());

        // Create a Button to edit logs (visible for HR access)
        editLogsButton = new Button("Edit Logs");
        editLogsButton.setOnAction(event -> editEffortLogs());
        editLogsButton.setVisible(true); // Always visible in HR mode

        // Create a Button to view logs
        viewLogsButton = new Button("View Logs");
        viewLogsButton.setOnAction(event -> viewEffortLogs());

        // Create a Button to view names
        Button viewNameButton = new Button("View Name");
        viewNameButton.setOnAction(event -> viewUserName());

        // Create a TextArea to display effort logs
        logTextArea = new TextArea();
        logTextArea.setEditable(false);

        // Create a ComboBox for user selection
        userComboBox = new ComboBox<>();
        userComboBox.getItems().addAll("User 1", "User 2", "User 3");
        userComboBox.setValue("Choose Employee");

        // Create a Label for displaying the name
        nameLabel = new Label("Employee Name: ");

        // Add UI elements to the VBox
        root.getChildren().addAll(
                userComboBox,
                actionField,
                timeSpentField,
                logButton,
                editLogsButton,
                viewLogsButton,
                viewNameButton,
                nameLabel,
                logTextArea
        );

        // Initially, set the name label to be invisible
        nameLabel.setVisible(false);

        // Create a Scene and set it in the stage
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    private void logEffort() {
        String user = userComboBox.getValue();
        String action = actionField.getText();
        float timeSpent = Float.parseFloat(timeSpentField.getText());

        String logEntry = "User: " + user + ", Action: " + action + ", Time Spent: " + timeSpent;
        effortLogs.add(logEntry);

        // Add audit trail entry
        String auditEntry = user + " logged effort: " + logEntry;
        auditTrail.add(auditEntry);

        // Clear input fields
        actionField.clear();
        timeSpentField.clear();

        // Display the name
        nameLabel.setText("Employee Name: " + user);
        nameLabel.setVisible(true);
    }

    private void viewUserName() {
        String user = userComboBox.getValue();
        String userName = "";

        switch (user) {
            case "User 1":
                userName = "Robert Carter";
                break;
            case "User 2":
                userName = "Jane Smith";
                break;
            case "User 3":
                userName = "Bob Johnson";
                break;
        }

        // Display the name
        nameLabel.setText("Employee Name: " + userName);
        nameLabel.setVisible(true);
    }

    private void viewEffortLogs() {
        String user = userComboBox.getValue();
        logTextArea.clear();

        // Show the user's label when viewing logs, only for HR
        if (user.equals("HR")) {
            nameLabel.setVisible(true);
        } else {
            nameLabel.setVisible(false);
        }

        // Display effort logs
        for (String log : effortLogs) {
            logTextArea.appendText(log + "\n");
        }
    }

    private void editEffortLogs() {
        String user = "HR"; // Assuming only HR can access this method

        // Display a dialog to select an entry to edit
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Log Entry");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the index of the log entry to edit:");

        // Get the user's input
        Optional<String> result = dialog.showAndWait();

        // Process the user's input
        result.ifPresent(indexStr -> {
            try {
                int index = Integer.parseInt(indexStr);

                if (index >= 0 && index < effortLogs.size()) {
                    String currentLog = effortLogs.get(index);

                    // Display another dialog to edit the selected entry
                    TextInputDialog editDialog = new TextInputDialog(currentLog);
                    editDialog.setTitle("Edit Log Entry");
                    editDialog.setHeaderText(null);
                    editDialog.setContentText("Edit the log entry:");

                    // Get the edited log entry
                    Optional<String> editedResult = editDialog.showAndWait();

                    // Update the log if the user provided an edit
                    editedResult.ifPresent(editedLog -> {
                        effortLogs.set(index, editedLog);

                        // Add audit trail entry for the edit
                        String auditEntry = user + " edited log entry at index " + index + ": " + editedLog;
                        auditTrail.add(auditEntry);
                    });
                } else {
                    // Handle index out of bounds
                    showAlert("Invalid Index", "Please enter a valid index.");
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
    }

    // Helper method to show an alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
