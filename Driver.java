package handler;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

/**
 *
 * @author Yatin Kewale
 *
 */
public class Driver {
	// static
	private static final Logger _Logger = Logger.getLogger(Driver.class.getName());

	// private
	private static CoapCli _cp;

	// constructors
	// static methods
	public static void start2() throws MqttException, IOException {

		_cp = new CoapCli(); // instantiating new COAP Client
		String x = "RAESSL"; // TOPIC
		Webcam webcam = Webcam.getDefault(); // Instantiate new Webcam Object
		webcam.setViewSize(WebcamResolution.VGA.getSize()); // Resolution for images to be captured
		WebcamPanel wbpnl = new WebcamPanel(webcam); // call webpanel method to create a new screen
		JFrame frame = new JFrame(); // new Java Frame to display the panel
		frame.add(wbpnl); // add the panel to the frame
		frame.setLocationRelativeTo(null); // Image centred
		frame.pack(); // Occupies entire frame
		frame.setVisible(true);// Frame visible
		int i = 0;
		while (i < 15) { // loop for taking 15 images each every 0.5 seconds
			try {
				Thread.sleep(500);

			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			i++;
			ImageIO.write(webcam.getImage(), "PNG", new File(i + "hello-world.png"));
		}
		webcam.close();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String y = dateFormat.format(cal.getTime());
		JSONObject jsonObj = new JSONObject( // creating a JSON message
				"{\"Category\":\"HomeSecurity\",\"What\":\"Somebody out there!\",\"Where\":\"at Darling street\",\"When\":\"now\"}");
		String z = (" with sensors Triggered at time =  " + y + "}");
		CoapCommServer ab = new CoapCommServer(x); // passing resource name to new coap server object
		ab.start();

		_cp.getMessage(x);
		_cp.postMessage(x, jsonObj.toString() + z);
		// _cp.getMessage(x);
		// _cp.putMessage(x, "Hi there");

		ReceivingApp r = new ReceivingApp();
		r.connect();
		// r.subscribeMessage(null);
		r.subscribeMessage(x);
		// r.subscribeMessage("/MDAS");
		// r.subscribeMessage("SafeDoor");
		// r.subscribeMessage("/SafeDoor");
		// _cp.discoverMessage();
		// _cp.deleteMessage(x);
		_Logger.log(Level.SEVERE, "Done%");
		ab.stop();

	}
}
