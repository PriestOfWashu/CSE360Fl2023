module EffortLoggerLogin {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens effortLoggerLogin to javafx.graphics, javafx.fxml;
}
