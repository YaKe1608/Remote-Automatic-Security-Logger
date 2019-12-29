package handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Yatin Kewale
 *
 */
public class ReceivingApp implements MqttCallback {
	public static final String DEFAULT_PROTOCOL = "tcp";
	// public static final String DEFAULT_HOST = "test.mosquitto.org";
	public static final String DEFAULT_HOST = "iot.eclipse.org";
	public static final int DEFAULT_PORT = 1883;
	private static final Logger _Logger = Logger.getLogger(ReceivingApp.class.getName());
	// params
	private String _clientID;
	private String _protocol;
	private String _host;
	private int _port;
	private String _brokerAddr;
	private MqttClient _client;

	// constructors
	public ReceivingApp() {
		super();
		_clientID = MqttClient.generateClientId();
		_protocol = DEFAULT_PROTOCOL;
		_host = DEFAULT_HOST;
		_port = DEFAULT_PORT;
		_brokerAddr = DEFAULT_PROTOCOL + "://" + DEFAULT_HOST + ":" + DEFAULT_PORT;
		_Logger.info("Broker Address: " + _brokerAddr);
	}

	// public methods

	public boolean disconnect() {
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

	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception { //public method to get the received messages and check the source
		

		System.out.println(topic + " is the group topic with message " + msg);
		if (topic == "/MDAS") {
			//_client.subscribe(topic);
			System.out.println(" Janvi's security system sending");
		} else if (topic == "/SafeDoor") {
			//_client.subscribe(topic);
			System.out.println(" Akash's security system sending data");
		} else if(topic=="RAESSL"){
			
			System.out.println(" Its my own data");
		}
		else System.out.println("Some common topic we have ");
		// TODO Auto-generated method stub
	}

	public boolean connect() {
		MemoryPersistence persistence = new MemoryPersistence();
		boolean success = false;
		try {
			System.out.println("in MQTT CLIENT=============================");
			// System.out.println("Got this "+ path);
			_client = new MqttClient(_brokerAddr, _clientID, persistence); //creating new MQTT object with required parameters
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
		} catch (MqttException e) {
			_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr);
		}
		return success;// TODO Auto-generated method stub

	}
	//public method which takes a string as argument and subscribes to the topic mentioned by the string
	public boolean subscribeMessage(String topic) throws MqttException {
		{
			try {
				_Logger.info("Subscribing to  " + topic);
				 _client.subscribeWithResponse(topic);
				_client.subscribe(topic);      //subscribing to various topics for test
				// _client.subscribe("SafeDoor");
				Thread.sleep(2000);
				// _client.unsubscribe(topic);
				// _Logger.info("Client Unsubscribed to topic: " + topic);
			} catch (Exception e) {
				_Logger.log(Level.SEVERE, "Failed to subscribe sync message", e);
			}
		}
		return true;
	}

}
