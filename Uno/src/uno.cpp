#include "core/Arduino.h"
#include "task/WebSocketTask.h"
#include "util.h"
WebSocketTask task;
void setup()
{
#ifdef DEBUG
	Serial.begin(9600);
#endif
	debug("start....");
	task.doTask();
}

void loop()
{
	//Serial.println("loop");
	delay(1000);
	task.monitor();
}

