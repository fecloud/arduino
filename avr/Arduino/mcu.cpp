#include "OneWire.h"
#include "DallasTemperature.h"
#include <SPI.h>
#include <Ethernet.h>

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(74,125,232,128);  // numeric IP for Google (no DNS)
char server[] = "fecloud.aliapp.com";    // name address for Google (using DNS)

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(10, 0, 0, 10);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

// Data wire is plugged into port 2 on the Arduino
#define ONE_WIRE_BUS 9
#define TEMPERATURE_PRECISION 9

// Setup a oneWire instance to communicate with any OneWire devices (not just Maxim/Dallas temperature ICs)
OneWire oneWire(ONE_WIRE_BUS);

// Pass our oneWire reference to Dallas Temperature.
DallasTemperature sensors(&oneWire);

int numberOfDevices; // Number of temperature devices found

DeviceAddress tempDeviceAddress; // We'll use this variable to store a found device address

// function to print a device address
void printAddress(DeviceAddress deviceAddress) {
	for (uint8_t i = 0; i < 8; i++) {
		if (deviceAddress[i] < 16)
			Serial.print("0");
		Serial.print(deviceAddress[i], HEX);
	}
}

void setup(void) {
	// start serial port
	Serial.begin(9600);

	// start the Ethernet connection:
	if (Ethernet.begin(mac) == 0) {
		Serial.println("Failed to configure Ethernet using DHCP");
		// no point in carrying on, so do nothing forevermore:
		// try to congifure using IP address instead of DHCP:
		Ethernet.begin(mac, ip);
	}

	Serial.println("Dallas Temperature IC Control Library Demo");

	// Start up the library
	sensors.begin();

	// Grab a count of devices on the wire
	numberOfDevices = sensors.getDeviceCount();

	// locate devices on the bus
	Serial.print("Locating devices...");

	Serial.print("Found ");
	Serial.print(numberOfDevices, DEC);
	Serial.println(" devices.");

	// report parasite power requirements
	Serial.print("Parasite power is: ");
	if (sensors.isParasitePowerMode())
		Serial.println("ON");
	else
		Serial.println("OFF");

	// Loop through each device, print out address
	for (int i = 0; i < numberOfDevices; i++) {
		// Search the wire for address
		if (sensors.getAddress(tempDeviceAddress, i)) {
			Serial.print("Found device ");
			Serial.print(i, DEC);
			Serial.print(" with address: ");
			printAddress(tempDeviceAddress);
			Serial.println();

			Serial.print("Setting resolution to ");
			Serial.println(TEMPERATURE_PRECISION, DEC);

			// set the resolution to 9 bit (Each Dallas/Maxim device is capable of several different resolutions)
			sensors.setResolution(tempDeviceAddress, TEMPERATURE_PRECISION);

			Serial.print("Resolution actually set to: ");
			Serial.print(sensors.getResolution(tempDeviceAddress), DEC);
			Serial.println();
		} else {
			Serial.print("Found ghost device at ");
			Serial.print(i, DEC);
			Serial.print(
					" but could not detect address. Check power and cabling");
		}
	}

}

// function to print the temperature for a device
void printTemperature(DeviceAddress deviceAddress) {
	// method 1 - slower
	//Serial.print("Temp C: ");
	//Serial.print(sensors.getTempC(deviceAddress));
	//Serial.print(" Temp F: ");
	//Serial.print(sensors.getTempF(deviceAddress)); // Makes a second call to getTempC and then converts to Fahrenheit

	// method 2 - faster
	float tempC = sensors.getTempC(deviceAddress);
	Serial.print("Temp C: ");
	Serial.println(tempC);
	//Serial.print(" Temp F: ");
	//Serial.println(DallasTemperature::toFahrenheit(tempC)); // Converts tempC to Fahrenheit
}

void uploadToServer(float temperature) {
	// if you get a connection, report back via serial:

	if (client.connect(server, 80)) {
		Serial.println("connected");
		// Make a HTTP request:
		client.print("GET /ds18b20?temperature=");
		client.print(temperature);
		client.println(" HTTP/1.1");
		client.println("Host: fecloud.aliapp.com");
		client.println("Connection: close");
		client.println();
		delay(1000);
		// if there are incoming bytes available
		// from the server, read them and print them:
		while (client.available()) {
			char c = client.read();
			Serial.print(c);
		}

	} else {
		// kf you didn't get a connection to the server:
		Serial.println("connection failed");
	}

	// if the server's disconnected, stop the client:
//	if (!client.connected()) {
//		Serial.println();
		Serial.println("disconnecting.");
		client.stop();
//	}

	Serial.println("uploadToServer end");
	Serial.println();
}

void loop(void) {
	// call sensors.requestTemperatures() to issue a global temperature
	// request to all devices on the bus
	Serial.print("Requesting temperatures...");
	sensors.requestTemperatures(); // Send the command to get temperatures
	Serial.println("DONE");

	// Loop through each device, print out temperature data
	for (int i = 0; i < numberOfDevices; i++) {
		// Search the wire for address
		if (sensors.getAddress(tempDeviceAddress, i)) {
			// Output the device ID
			Serial.print("Temperature for device: ");
			Serial.println(i, DEC);

			// It responds almost immediately. Let's print out the data
			printTemperature(tempDeviceAddress); // Use a simple function to print out the data
			uploadToServer(sensors.getTempC(tempDeviceAddress));
		}
		//else ghost device! Check your power requirements and cabling

	}
	delay(60000);
}

