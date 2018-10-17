/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialmonitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vrizaldi
 */
public class SerialMonitor extends Application {

	private static final Logger LOGGER = Logger.getLogger(SerialMonitor.class.getName());

	private ConnectionManager connectionManager;

	private Button connectBtn;
	private TextArea messageBox;
	private TextArea messageField;
	private ComboBox baudRateDrop;
	private Button sendBtn;
	
	@Override
	public void start(Stage primaryStage) {

		connectionManager = new ConnectionManager();

		this.initUIParts();
		this.initEventHandlers();
		this.initLayout(primaryStage);
    }



	// UI methods
	private void initUIParts() {
		// initialise the ui parts

		// connect button
		this.connectBtn = new Button("Connect");
		this.connectBtn.setPrefSize(400, 50);
		
		// baud rate list
		this.baudRateDrop = new ComboBox();
		this.baudRateDrop.getItems().addAll(
			"110 bauds", "300 bauds", "600 bauds", "1200 bauds", "2400 bauds", 
			"4800 bauds", "9600 bauds", "14400 bauds", "19200 bauds", "38400 bauds", 
			"57600 bauds", "115200 bauds", "128000 bauds", "256000 bauds"
		);
		this.baudRateDrop.setValue("9600 bauds");
		this.baudRateDrop.setPrefSize(110, 50);
		
		// message box
		this.messageBox = new TextArea();
		this.messageBox.setPrefSize(500, 500);
		this.messageBox.setEditable(false);
		this.messageBox.setFocusTraversable(false);
		
		// for entering messages
		this.messageField = new TextArea();	// message field
		this.messageField.setPrefSize(425, 50);

		// send button
		this.sendBtn = new Button("Send");				
		this.sendBtn.setPrefSize(85, 50);
	}

	private void initLayout(Stage primaryStage) {
		// initialise the layout for the ui parts
	
		HBox connectionSetting = new HBox();
		connectionSetting.getChildren().addAll(this.connectBtn, this.baudRateDrop);

		HBox userInput = new HBox();
		userInput.getChildren().addAll(messageField, sendBtn);
                
		VBox root = new VBox();
		root.getChildren().addAll(connectionSetting, this.messageBox, userInput);
                
		Scene scene = new Scene(root, 500, 600);

		primaryStage.setTitle("Serial terminal");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(500);
		primaryStage.setMaxHeight(600);
		primaryStage.show();
	}
	
	

	// event handlers
	private void initEventHandlers() {
		connectBtn.setOnAction((e) -> this.connect(e));
		sendBtn.setOnAction((e) -> this.sendMessage(e));
	}

	/*"110 baud", "300 baud", "600 baud", "1200 baud", "2400 baud", 
			"4800 baud", "9600 baud", "14400 baud", "19200 baud", "38400 baud", 
			"57600 baud", "115200 baud", "128000 baud", "256000 baud"*/
	private void connect(ActionEvent e) {
		String baudRateStr = this.baudRateDrop.getValue().toString();
		LOGGER.log(Level.INFO, "Baud rates set to {0}, connecting...", baudRateStr);

		int baudRate = 0;
		if(baudRateStr.contains("110")) baudRate = 110;
		else if(baudRateStr.contains("300")) baudRate = 300;
		else if(baudRateStr.contains("600")) baudRate = 600;
		else if(baudRateStr.contains("1200")) baudRate = 1200;
		else if(baudRateStr.contains("2400")) baudRate = 2400;
		else if(baudRateStr.contains("4800")) baudRate = 4800;
		else if(baudRateStr.contains("9600")) baudRate = 9600;
		else if(baudRateStr.contains("14400")) baudRate = 14400;
		else if(baudRateStr.contains("19200")) baudRate = 19200;
		else if(baudRateStr.contains("38400")) baudRate = 38400;
		else if(baudRateStr.contains("57600")) baudRate = 57600;
		else if(baudRateStr.contains("115200")) baudRate = 115200;
		else if(baudRateStr.contains("128000")) baudRate = 128000;
		else if(baudRateStr.contains("256000")) baudRate = 256000;

		connectionManager.connect(baudRate);
	}

	private void sendMessage(ActionEvent e) {
		LOGGER.info("Sending message...");
	}



	
    /**
     * @param args the command line arguments
     */
	public static void main(String[] args) {
        launch(args);
    }
    
}