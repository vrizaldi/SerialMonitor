/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialmonitor;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortList;

/**
 *
 * @author vrizaldi
 */
class ConnectionManager {

	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

	public ConnectionManager() {
		LOGGER.log(Level.INFO, "Available ports: {0}", Arrays.toString(SerialPortList.getPortNames()));
	}

	void connect(int baudRate) {
		LOGGER.log(Level.INFO, "Connecting with baud rate of {0}", baudRate);
	}
}
