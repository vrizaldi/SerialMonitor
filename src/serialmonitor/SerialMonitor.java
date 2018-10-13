/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialmonitor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vrizaldi
 */
public class SerialMonitor extends Application {
    
    @Override
    public void start(Stage primaryStage) {
		// connect button
		Button connectBtn = new Button("Connect");
		
		// message box
		TextArea messageBox = new TextArea();
		messageBox.setPrefRowCount(20);
		messageBox.setPrefSize(500, 550);
		messageBox.setEditable(false);
		
		// for entering messages
		TextArea messageField = new TextArea();	// message field
		messageField.setPrefSize(415, 50);

		// send button
		Button sendBtn = new Button("Send");				
		sendBtn.setPrefSize(415, 50);
                
		HBox userInput = new HBox();
		userInput.getChildren().addAll(messageField, sendBtn);
		userInput.setSpacing(5);
                
		VBox root = new VBox();
		root.getChildren().addAll(connectBtn, messageBox, userInput);
                
		Scene scene = new Scene(root, 500, 600);

		primaryStage.setTitle("Serial terminal");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(500);
		primaryStage.setMaxHeight(600);
		primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
