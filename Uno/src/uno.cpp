#include "core/Arduino.h"
#include "task/WebSocketTask.h"

WebSocketTask task;
void setup()
{
	Serial.begin(9600);
	Serial.print("start....");
	task.doTask();
}

void loop()
{
	//Serial.println("loop");
	task.monitor();
}

