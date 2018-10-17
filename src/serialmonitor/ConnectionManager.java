/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialmonitor;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author vrizaldi
 */
class ConnectionManager {

	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

	private SerialPort serialPort;

	public ConnectionManager() {
		LOGGER.log(Level.INFO, "Available ports: {0}", Arrays.toString(SerialPortList.getPortNames()));

		serialPort = new SerialPort("COM6");
	}

	void connect(String baudRateStr) {

		if(serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch(SerialPortException e) {
				LOGGER.log(Level.SEVERE, "Error occurred when closing port: {0}", e.toString());
			}
		}


		int baudRate = ConnectionManager.strToBaudRateConst(baudRateStr);
		try {
			LOGGER.log(Level.INFO, "Connecting with baud rate of {0}...", baudRateStr);
			serialPort.openPort();
			serialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			LOGGER.log(Level.INFO, "Connection successful", baudRateStr);

		} catch(SerialPortException e) {
			LOGGER.log(Level.SEVERE, "Error occurred when connecting: {0}", e.toString());
		}
	}
	
	static int strToBaudRateConst(String baudRateStr) {
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
}
