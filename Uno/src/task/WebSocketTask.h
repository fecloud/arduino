/*
 * WebSocketTask.h
 *
 *  Created on: 2014年4月17日
 *      Author: Feng OuYang
 */

#ifndef WEBSOCKETTASK_H_
#define WEBSOCKETTASK_H_

#include "Task.h"
#include "core/Arduino.h"
#include "Ethernet/Ethernet.h"
#include "SPI/SPI.h"
#include "websocket/WebSocketClient.h"

byte mac[] =
{ 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
const char *server = "111.206.45.12";
IPAddress ip(10, 0, 0, 9);

class WebSocketTask: public Task
{
private:
		WebSocketClient client;
		int conneted_server();

public:
	virtual void doTask();
	void monitor();
};

#endif /* WEBSOCKETTASK_H_ */



