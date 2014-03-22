#include "Arduino.h"
#include <Ethernet.h>
#include <SPI.h>
#include "WebSocketClient.h"
#include "uno.h"

byte mac[] =
{ 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
const char *server = "10.0.0.2";
WebSocketClient client;
Queue *pq;

void invoke(void (*in)())
{
	(*in)();
}

void dataArrived(WebSocketClient client, String data)
{
	Serial.println("Data Arrived: " + data);
	client.send("ok");
}

int conneted_server()
{
	Ethernet.begin(mac);
	client.connect(server);
	client.setDataArrivedDelegate(dataArrived);
	return 1;
}

void setup()
{
	Serial.begin(9600);
	pq = InitQueue();
	EnQueue(pq, (void*) conneted_server);
}

void loop()
{
	if (!IsEmpty(pq))
	{
		Item item;
		DeQueue(pq, &item);

		invoke(item);
	}

	client.monitor();
}

