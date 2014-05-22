/*
 * WebSocketTask.cpp
 *
 *  Created on: 2014年4月17日
 *      Author: Feng OuYang
 */
#include "util.h"
#include "WebSocketTask.h"

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
const char *server = "111.206.45.12";
IPAddress ip(10, 0, 0, 9);

void dataArrived(WebSocketClient client, String data) {
	Serial.println("Data Arrived: " + data);
}

int WebSocketTask::conneted_server() {
	Ethernet.begin(mac, ip);
	Serial.println(Ethernet.localIP());
	char device_name[] = { "Arduino uno" };
	while (1) {
		if (client.connect(server, device_name, 30156)) {
			break;
		} else {
			client.disconnect();
			delay(1000);
			debug("try connet");
		}
	}
	client.setDataArrivedDelegate(dataArrived);
	return 1;
}

void WebSocketTask::doTask() {
	debug("doTask");

	while(true){
		int i  =  conneted_server();
		if(i > 0){
			break;
		}
	}

}

void WebSocketTask::monitor() {
	//client.send("123");
	client.monitor();
}
