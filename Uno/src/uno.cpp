#include "core/Arduino.h"
#include "task/WebSocketTask.h"

WebSocketTask task;
void setup()
{
	Serial.begin(9600);
	Serial.print("start....");
}

void loop()
{
	Serial.println("loop");
	task.doTask();
}

