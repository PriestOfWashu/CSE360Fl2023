module EffortLoggerLogin {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires jasypt;
	
	opens effortLogger_V2 to javafx.graphics, javafx.fxml;
}
