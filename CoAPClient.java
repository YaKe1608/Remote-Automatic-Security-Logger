package handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class CoapCli {
	private CoapClient _client;
	private static final String DEFAULT_PROTOCOL = "coap";
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 5683;
	private static final Logger _Logger = Logger.getLogger(Driver.class.getName());
	private String _protocol;
	private String _host;
	private int _port;
	private String _serverAddr;

	// public constructor to set parameters
	public CoapCli() {
		super();
		_protocol = DEFAULT_PROTOCOL;
		_host = DEFAULT_HOST;
		_port = DEFAULT_PORT;
		_serverAddr = DEFAULT_PROTOCOL + "://" + DEFAULT_HOST + ":" + DEFAULT_PORT;
		_Logger.info("Broker address: " + _serverAddr);
	}

	// public method to implement GET
	public void getMessage(String topic) {
		System.out.println("getting mesage");
		try {
			if (_client == null) { // if client is null, a new object is created with parameters provided to
									// prevent null exception
				_client = new CoapClient(_serverAddr);
			} else {
				_client.setURI(_serverAddr + "/" + topic); // sets uniform resource identifier for the instantiated
															// object
				System.out.println(_client.getURI());
			}
			_client.get();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Something went wrong!");
		}
	}

	// public method to implement POST
	public void postMessage(String topic, String string2) {

		try {
			System.out.println("posting mesage for URI " + topic + " " + string2);
			if (_client == null) {
				_client = new CoapClient(_serverAddr);// if client is null, a new object is created with parameters
														// provided to prevent null exception
			} else {
				_client.setURI(_serverAddr + "/" + topic);// sets uniform resource identifier for the instantiated
															// object
				System.out.println("posting for " + _client.getURI());
			}
			_client.post(string2, MediaTypeRegistry.TEXT_PLAIN);
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Something went wrong!");
		}
	}

	// public method to implement DISCOVER
	public void discoverMessage(String topic) {
		System.out.println("discovering");
		try {
			if (_client == null) {
				_client = new CoapClient(_serverAddr);// if client is null, a new object is created with parameters
														// provided to prevent null exception
			} else {
				_client.setURI(_serverAddr + "/" + topic);// sets uniform resource identifier for the instantiated
															// object
			}
			_client.discover();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Something went wrong!");
		}
	}

	// public method to implement PUT
	public void putMessage(String topic, String string2) {
		System.out.println("Putting mesage");
		try {
			if (_client == null) {
				_client = new CoapClient(_serverAddr);// if client is null, a new object is created with parameters
														// provided to prevent null exception
			} else {
				_client.setURI(_serverAddr + "/" + topic);// sets uniform resource identifier for the instantiated
															// object
			}
			_client.put(topic, MediaTypeRegistry.TEXT_PLAIN);
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Something went wrong!");
		}
	}

	// public method to implement DELETE
	public void deleteMessage(String topic) {
		System.out.println("deleting mesage");
		try {
			if (_client == null) {
				_client = new CoapClient(_serverAddr);// if client is null, a new object is created with parameters
														// provided to prevent null exception
			} else {
				_client.setURI(_serverAddr + "/" + topic);// sets uniform resource identifier for the instantiated
															// object
			}
			_client.delete();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Something went wrong!");
		}
	}

	public String getPath(String x) {

		return x;
	}
}
