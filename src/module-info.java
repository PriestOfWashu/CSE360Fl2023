module EffortLoggerLogin {
	// JavaFX Controls, FXML, Graphics, and Base are required for JavaFX functionality.
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	// Jasypt library utilized for login file encryption.
	requires jasypt;
	
	opens effortLogger_V2 to javafx.graphics, javafx.fxml;
}
