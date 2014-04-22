#include "core/Arduino.h"
#include "Ethernet/Ethernet.h"
#include "SPI/SPI.h"
#include "WebSocketClient.h"

byte mac[] =
{ 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
const char *server = "111.206.45.12";
IPAddress ip(10, 0, 0, 9);
WebSocketClient client;

void dataArrived(WebSocketClient client, String data)
{
	Serial.println("Data Arrived: " + data);
	if (data.startsWith("led"))
	{
		Serial.println("led cmd");
		String v = data.substring(4);
		Serial.println(v);
		if (v == "1")
		{
			Serial.println("led HIGH");
			digitalWrite(2, HIGH);
		}
		else
		{
			Serial.println("led LOW");
			digitalWrite(2, LOW);
		}
	}
	client.send("ok");
}

int conneted_server()
{
	Ethernet.begin(mac, ip);
	Serial.println(Ethernet.localIP());
	char device_name[] =
	{ "Arduino uno" };
	while (1)
	{
		if (client.connect(server, device_name, 30156))
		{
			break;
		}
		else
		{
			delay(1000);
			Serial.println("try connet");
		}
	}
	client.setDataArrivedDelegate(dataArrived);
	return 1;
}

void setup()
{
	Serial.begin(9600);
	int i = conneted_server();
	Serial.println(i);
}

void loop()
{
	client.monitor();
}

