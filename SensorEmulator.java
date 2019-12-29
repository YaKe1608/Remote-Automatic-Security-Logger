package handler;

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

public class SensorEmulator {
	public static void main(String[] args) throws MqttException, IOException {
		System.out.println("Into Sensor Emulator  ");
		boolean t = true;

		while (t) {
			Scanner reader = new Scanner(System.in);
			System.out.println("Sensor is ON -> ");
			String s = reader.next(); //Sensor number 1
			String d = reader.next(); //Sensor number 2
			System.out.println("you have pressed  ->" + s + " " + d + " " + " which causes");

			while (s != null || d != null) {
				if (s.equals("q") && d.equals("w")) { // first sensor triggered before second sensors,hence someone
														// coming from outside
					System.out.println(" Sensor is Triggered -> Person going IN Hence ->Calling the Driver class ");
					Driver.start2();
					break;

				} else if (s.equals("w") && d.equals("q")) { // first sensor triggered after second . Hence somebody
																// going out
					System.out.println("Sensor is Triggered -> Person going OUT Hence ->Calling the Driver claas");
					Driver.start2();
					break;
				}

				else {
					System.out.println("Nothing because its Just a USELESS INPUT ");
					System.out.println("Not the Sensors I designed!");

					break;
				}
			}

		}
	}

}
