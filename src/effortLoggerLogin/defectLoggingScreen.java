package defectLoggingScreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
 
public class defectLoggingScreen extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee/Manager Defect Logging Screen");
        
        TextField priority = new TextField();
        priority.setMaxWidth(500);
        priority.setTranslateY(-155);
        priority.setPromptText("Priority");
        priority.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
        TextField stepsToReproduce = new TextField();
        stepsToReproduce.setMaxWidth(500);
        stepsToReproduce.setTranslateY(-75);
        stepsToReproduce.setMinHeight(100);
        stepsToReproduce.setPromptText("Steps to Reproduce");
        stepsToReproduce.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
        TextField description = new TextField();
        description.setMaxWidth(500);
        description.setTranslateY(40);
        description.setMinHeight(100);
        description.setPromptText("Description");
        description.setStyle("-fx-border-color: black ; -fx-border-width: 2px;");
        Button btn = new Button();
        btn.setMaxWidth(100);
        btn.setTranslateY(125);
        btn.setText("Submit");
        btn.setBackground(null);
        btn.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
        btn.setOnAction(new EventHandler<>() {
        	@Override
            public void handle(ActionEvent event) {
                System.out.println("Go to next page here");
                String priorityText = priority.getText();
                String stepsToReproduceText = stepsToReproduce.getText();
                String descriptionText = description.getText();
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("You entered:\n\n"
                		+ priorityText +"\n"
                		+ stepsToReproduceText +"\n"
                		+ descriptionText));
                Scene dialogScene = new Scene(dialogVbox, 400, 100);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        Button questionMark = new Button();
        questionMark.setTranslateX(370);
        questionMark.setTranslateY(225);
        questionMark.setText("?");
        questionMark.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
        questionMark.setBackground(null);
        questionMark.setOnAction(new EventHandler<>() {
        	@Override
        	public void handle(ActionEvent event) {
        		final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("Tutorial of this page:\n"
                		+ "\nEnter the defect's priority in the first text field"
                		+ "\nEnter the steps to reproduce this defect in the second text field"
                		+ "\nEnter the description of this defect in the third text field"));
                Scene dialogScene = new Scene(dialogVbox, 400, 100);
                dialog.setScene(dialogScene);
                dialog.show();
        	}
        });
        
        
        StackPane root = new StackPane();
        root.getChildren().add(priority);
        root.getChildren().add(stepsToReproduce);
        root.getChildren().add(description);
        root.getChildren().add(btn);
        root.getChildren().add(questionMark);
        Scene test = new Scene(root, 800, 500);
        test.setFill(Color.RED);
        primaryStage.setScene(test);
        //primaryStage.setScene(new Scene (root, 1000, 500)); 
        primaryStage.show();
        
    }
}