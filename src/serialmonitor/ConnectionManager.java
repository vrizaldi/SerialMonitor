/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialmonitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

/**
 *
 * @author vrizaldi
 */
class ConnectionManager {

	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

	private SerialPort serialPort;
	private SerialMonitor serialMonitor;

	public ConnectionManager(SerialMonitor serialMonitor) {
		this.serialMonitor = serialMonitor;
	}



	// connecting
	boolean connect(final String port, final String baudRateStr) {

		// close previously opened port
		if(serialPort != null && serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch(SerialPortException e) {
				LOGGER.log(Level.SEVERE, "Error occurred when closing port: {0}", e.toString());
				return false;
			}
		}


		// convert baud rate into serial port baud rate constants
		int baudRate = ConnectionManager.strToBaudRateConst(baudRateStr);
		if(baudRate == -1) return false;

		// connect
		try {
			LOGGER.log(Level.INFO, "Connecting through {0} with baud rate of {1}...", 
				new Object[]{port, baudRateStr});
			serialPort = new SerialPort(port);
			serialPort.openPort();
			serialPort.setParams(
				baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.addEventListener((e) -> this.receiveMessage(e));

		} catch(SerialPortException e) {
			LOGGER.log(Level.SEVERE, "Error occurred when connecting: {0}", e.toString());
			return false;
		}


		LOGGER.log(Level.INFO, "Connection successful");
		return true;
	}
	
	static int strToBaudRateConst(final String baudRateStr) {
		if(baudRateStr.contains("110")) return SerialPort.BAUDRATE_110;
		else if(baudRateStr.contains("300")) return SerialPort.BAUDRATE_300;
		else if(baudRateStr.contains("600")) return SerialPort.BAUDRATE_600;
		else if(baudRateStr.contains("1200")) return SerialPort.BAUDRATE_1200;
		else if(baudRateStr.contains("4800")) return SerialPort.BAUDRATE_4800;
		else if(baudRateStr.contains("9600")) return SerialPort.BAUDRATE_9600;
		else if(baudRateStr.contains("14400")) return SerialPort.BAUDRATE_14400;
		else if(baudRateStr.contains("19200")) return SerialPort.BAUDRATE_19200;
		else if(baudRateStr.contains("38400")) return SerialPort.BAUDRATE_38400;
		else if(baudRateStr.contains("57600")) return SerialPort.BAUDRATE_57600;
		else if(baudRateStr.contains("115200")) return SerialPort.BAUDRATE_115200;
		else if(baudRateStr.contains("128000")) return SerialPort.BAUDRATE_128000;
		else if(baudRateStr.contains("256000")) return SerialPort.BAUDRATE_256000;
		else return -1;
	}



	// sending message
	void sendMessage(final String message) {
		try {
			this.serialPort.writeString(message);
		} catch(SerialPortException e) {
			LOGGER.log(Level.SEVERE, "Error occurred when sending message: {0}", e.toString());
		}
	}



	// receiving message
	private void receiveMessage(SerialPortEvent e) {
		try {
			this.serialMonitor.receiveMessage(serialPort.readString());
		} catch(SerialPortException ex) {
			LOGGER.log(Level.SEVERE, "Error occurred when reading message: {0}", ex.toString());
		}
	}
}
