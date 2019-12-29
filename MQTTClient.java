package handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Yatin Kewale
 *
 */
public class ICommClient implements MqttCallback {
	public static final String DEFAULT_PROTOCOL = "tcp";
	// public static final String DEFAULT_HOST = "test.mosquitto.org";
	public static final String DEFAULT_HOST = "iot.eclipse.org";
	public static final int DEFAULT_PORT = 1883;
	private static final Logger _Logger = Logger.getLogger(ICommClient.class.getName());
	// params
	private String _clientID;
	private String _protocol;
	private String _host;
	private int _port;
	private String _brokerAddr;
	private MqttClient _client;

	// constructors
	public ICommClient() {
		super();
		_clientID = MqttClient.generateClientId();
		_protocol = DEFAULT_PROTOCOL;
		_host = DEFAULT_HOST;
		_port = DEFAULT_PORT;
		_brokerAddr = DEFAULT_PROTOCOL + "://" + DEFAULT_HOST + ":" + DEFAULT_PORT;
		_Logger.info("Broker Address: " + _brokerAddr);
	}

	// public methods

	public boolean disconnect() { // public method to disconnect Matt client for the given address
		boolean success = false;
		try {
			_client.disconnect();
			_Logger.info("Disconnected from Broker" + _brokerAddr);
			success = true;
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr);
		}
		return success;
	}

	public boolean sendMessage(String topic, int qosLevel, String msg) { // public method which returns true or false
																			// based on whether message is sent or not
		try {
			_Logger.info("Publishing message... ");
			MqttMessage message = new MqttMessage(msg.getBytes()); // getting message into ytes
			message.setQos(qosLevel);
			// _client.subscribeWithResponse(topic);
			// _client.subscribeWithResponse("/SafeDoor");
			_client.publish(topic, message);
			_Logger.info("Message sync published: " + message.getId() + " - " + message);
			Thread.sleep(2000);// wait for 2 seconds
			_Logger.info("Client Unsubscribed to topic: " + topic);
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to publish sync message", e);
		}
		return true;

	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception { // public method to print the received
																					// messages and checkc the source
		// TODO Auto-generated method stub
		System.out.println(topic + " is the group topic with message " + msg);
		if (topic == "/MDAS") {
			// _client.subscribe(topic);
			System.out.println(" Janvi's security system sending");
		} else if (topic == "/SafeDoor") {
			// _client.subscribe(topic);
			System.out.println(" Akash's security system sending data");
		} else if (topic == "RAESSL") {

			System.out.println(" Its my own data");
		} else
			System.out.println("Some common topic we have ");

	}

	public boolean connect() { // public method to connect to the MQTT broker
		MemoryPersistence persistence = new MemoryPersistence();
		boolean success = false;
		try {
			System.out.println("in MQTT CLIENT=============================");
			_client = new MqttClient(_brokerAddr, _clientID, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			_Logger.info("Connecting to a broker: " + _brokerAddr);
			_client.setCallback(this);
			_client.connect(connOpts);
			_Logger.info("Connected to a broker: " + _brokerAddr);
			long waitMillis = 650L;
			_Logger.info("Waiting at least " + waitMillis + " seconds for ping...");
			try {
				Thread.sleep(waitMillis);
			} catch (Exception e) {
				// ignore
			}
			// success = true;
			System.out.println("Out  of  MQTT CLIENT=============================");
		} catch (MqttException e) {// catches exception
			_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr);
		}
		return success;// TODO Auto-generated method stub

	}
}
