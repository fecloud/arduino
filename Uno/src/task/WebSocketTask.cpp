/*
 * WebSocketTask.cpp
 *
 *  Created on: 2014年4月17日
 *      Author: Feng OuYang
 */

#include "WebSocketTask.h"

void dataArrived(WebSocketClient client, String data)
{
	Serial.println("Data Arrived: " + data);
}

int WebSocketTask::conneted_server()
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


void WebSocketTask::doTask()
{
	#ifdef DEBUG
		Serial.println("doTask");
	#endif

	int i = conneted_server();

	#ifdef DEBUG
		Serial.println("i:" + i );
	#endif

}

void WebSocketTask::monitor()
{
	client.monitor();
}