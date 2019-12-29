package handler;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import handler.ICommClient;

public class CoapToMqttResourceHandler extends CoapResource {
	// static
	private static final Logger _Logger = Logger.getLogger(CoapToMqttResourceHandler.class.getName());
	public static final int DEFAULT_QOS_LEVEL = 2;

	private ICommClient _mqttClient;

	// constructors
	/**
	 * Constructor.
	 *
	 * @param name
	 *            Basically, the path (or topic)
	 */
	public CoapToMqttResourceHandler(String name) {
		super(name);
		_mqttClient = new ICommClient(); // instantiates a new MQTT Client object
		try {
			System.out.println("into resource hander with " + name);
			_mqttClient.connect(); // connects to the given MQTT broker address
		} catch (Exception e) {
			_Logger.warning("Failed to connect to MQTT broker.");
		}
	}

	// public methods
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.californium.core.CoapResource#handleDELETE(org.eclipse.
	 * californium.core.server.resources.CoapExchange)
	 */
	@Override
	public void handleDELETE(CoapExchange context) {
		System.out.println("handling delete");// TODO: handle DELETE as appropriate
		try {
			context.accept();
			String s = new String(context.getRequestPayload());
			if (_mqttClient.sendMessage(super.getURI(), DEFAULT_QOS_LEVEL, s)) {// checks if that message is modified
				// accordingly, send a corresponding
				// message. Here it is DELETE
				context.respond(ResponseCode.DELETED, "Deleted content.");
			} else {
				context.respond(ResponseCode.SERVICE_UNAVAILABLE, "Oops - can't delete content.");
				_Logger.warning("Failed to publish message to MQTT broker.");
			}
		} catch (Exception e) {
			_Logger.warning("Failed to handle DELETE!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.californium.core.CoapResource#handleGET(org.eclipse.californium.
	 * core.server.resources.CoapExchange)
	 */
	@Override
	public void handleGET(CoapExchange context) {
		// System.out.println("handling get");// TODO: handle GET as appropriate
		String responseMsg = "This is a generic response to the GET request for path: " + super.getName();
		context.accept(); // sends an acknowledgement to the client for successful reception of message
		context.respond(ResponseCode.VALID, responseMsg); // responds with suitable response message
		_Logger.info("Handling GET: " + responseMsg);
		_Logger.info(context.getRequestCode().toString() + ": " + context.getRequestText());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.californium.core.CoapResource#handlePOST(org.eclipse.californium.
	 * core.server.resources.CoapExchange)
	 */

	@Override
	public void handlePOST(CoapExchange context) {
		try {
			context.accept();
			System.out.println("handling post ");

			// System.out.println(context.getRequestText().toString());
			String s = new String(context.getRequestPayload()); // gets String for a byte payload
			if (_mqttClient.sendMessage(context.getRequestOptions().getUriPathString(), DEFAULT_QOS_LEVEL, s)) {// checks
				// if
				// that
				// message
				// is
				// modified
				// accordingly, send a corresponding
				// message. Here it is DELETE
				context.respond(ResponseCode.CREATED, "Created content.");
			} else {
				context.respond(ResponseCode.SERVICE_UNAVAILABLE, "Oops - can't create content.");
				_Logger.warning("Failed to publish message to MQTT broker in HandlePost.");
			}

		} catch (Exception e) {
			_Logger.warning("Failed to handle POST!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.californium.core.CoapResource#handlePUT(org.eclipse.californium.
	 * core.server.resources.CoapExchange)
	 */
	@Override
	public void handlePUT(CoapExchange context) {
		System.out.println("IN HANDLEPUT");
		// TODO: handle PUT as appropriate
		try {
			context.accept();
			System.out.println(super.getPath() + context.getRequestText());
			String s = new String(context.getRequestPayload());

			if (_mqttClient.sendMessage(context.getRequestOptions().getUriPathString(), DEFAULT_QOS_LEVEL, s)) {
				context.respond(ResponseCode.CREATED, "Changed content.");// checks if that message is modified
				// accordingly, send a corresponding
				// message. Here it is PUT
			} else {
				context.respond(ResponseCode.SERVICE_UNAVAILABLE, "Oops - can't change content.");
				_Logger.warning("Failed to publish message to MQTT broker in HandlePut.");
			}

		} catch (Exception e) {
			_Logger.warning("Failed to handle POST!");
		}
	}

	public String toString() {

		return null;
	}
}
