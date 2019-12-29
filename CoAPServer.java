package handler;

import java.util.logging.Level;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapServer;

public class CoapCommServer {
	// static
	private static final Logger _Logger = Logger.getLogger(CoapCommServer.class.getName());
	// params
	private CoapServer _server;

	// constructors
	public CoapCommServer() {
		this((String[]) null);
	}

	/**
	 *
	 * @param resourceNames
	 */
	public CoapCommServer(String... resourceNames) {
		super();
		_server = new CoapServer();
		if (resourceNames != null) {
			for (String resourceName : resourceNames) {
				CoapToMqttResourceHandler cmrh = new CoapToMqttResourceHandler(resourceName);// instantiate new
																								// CoapToMqttResourceHandler
																								// object with arguments
																								// as resourcename
				_server.add(cmrh);
				_Logger.info("Adding server resource handler: " + cmrh.getURI());

			}
		}
	}

	// public methods
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.labbenchstudios.iot.comm.ICommServer#start()
	 */
	public boolean start() { // method to start server
		boolean success = false;
		System.out.println("Server started");
		try {
			_server.start(); // Coap server is started

		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to start CoAP server.", e);
		}
		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.labbenchstudios.iot.comm.ICommServer#stop()
	 */
	public boolean stop() { // public method to stop the COAP server
		boolean success = false;
		try {
			System.out.println("server stopped");
			_server.stop(); // Coap server is stopped
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to stop CoAP server.", e);
		}
		return success;
	}

}
