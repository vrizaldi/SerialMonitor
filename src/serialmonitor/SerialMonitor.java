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
import jssc.SerialPortList;
import java.sql.Timestamp;
import javafx.stage.WindowEvent;

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
	private ComboBox portDrop;
	private Button sendBtn;

	private String messages;
	
	@Override
	public void start(Stage primaryStage) {

		connectionManager = new ConnectionManager(this);

		messages = "";

		this.initUIParts();
		this.initEventHandlers();
		this.initLayout(primaryStage);
    }



	// UI methods
	private void initUIParts() {
		// initialise the ui parts

		// connect button
		this.connectBtn = new Button("Connect");
		this.connectBtn.setPrefSize(300, 50);
		
		// baud rate list
		this.baudRateDrop = new ComboBox();
		this.baudRateDrop.getItems().addAll(
			"110 bauds", "300 bauds", "600 bauds", "1200 bauds", "2400 bauds", 
			"4800 bauds", "9600 bauds", "14400 bauds", "19200 bauds", "38400 bauds", 
			"57600 bauds", "115200 bauds", "128000 bauds", "256000 bauds"
		);
		this.baudRateDrop.setValue("Baud rate");
		this.baudRateDrop.setPrefSize(105, 50);

		// com port list
		this.portDrop = new ComboBox();
		this.portDrop.getItems().addAll((Object[])SerialPortList.getPortNames());
		this.portDrop.setValue("COM Port");
		this.portDrop.setPrefSize(105, 50);
		
		// message box
		this.messageBox = new TextArea();
		this.messageBox.setPrefSize(500, 500);
		this.messageBox.setEditable(false);
		this.messageBox.setFocusTraversable(false);
		this.messageBox.setWrapText(true);
		
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
		connectionSetting.getChildren().addAll(this.connectBtn, this.baudRateDrop, this.portDrop);

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
		primaryStage.setOnCloseRequest((e) -> this.terminate(e));
		primaryStage.show();
	}
	
	

	// event handlers
	private void initEventHandlers() {
		connectBtn.setOnAction((e) -> this.connect(e));
		sendBtn.setOnAction((e) -> this.sendMessage(e));
	}

	private void connect(ActionEvent e) {
		String baudRateStr = this.baudRateDrop.getValue().toString();
		String port = this.portDrop.getValue().toString();
		LOGGER.log(Level.INFO, "Baud rate set to {0}, connecting through {1}...", new Object[]{baudRateStr, port});

		if(!connectionManager.connect(port, baudRateStr)) {
			LOGGER.log(Level.INFO, "Connection failed");
		} else {
			LOGGER.log(Level.INFO, "Connection successful");
			this.connectBtn.setText("Reset connection");
				// change the connect button label
		}
	}

	private void sendMessage(ActionEvent e) {
		String message = this.messageField.getText() + "\n";
		this.messageField.setText("");		// empty message field
		LOGGER.log(Level.INFO, "Sending message: {0}", message);
		this.connectionManager.sendMessage(message);
		this.updateMessageBox("[SENT] " + message);
	}

	void receiveMessage(String message) {
		LOGGER.log(Level.INFO, "Received message: {0}", message);
		this.updateMessageBox("[RECEIVED] " + message);
	}

	private void terminate(WindowEvent e) {
		LOGGER.log(Level.INFO, "Terminating program...");
		this.connectionManager.terminate();
	}



	// update message box on changes
	private void updateMessageBox(String message) {

		this.messages += new Timestamp(System.currentTimeMillis()).toString() + " " + message;	
		this.messageBox.setText(messages);
		this.messageBox.setScrollTop(Double.MAX_VALUE);
	}


	
    /**
     * @param args the command line arguments
     */
	public static void main(String[] args) {
        launch(args);
    }
    
}