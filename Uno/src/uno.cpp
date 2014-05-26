#include "core/Arduino.h"
#include "task/WebSocketTask.h"
#include "queue/queue.h"
#include "util.h"
WebSocketTask task;
Queue<Task> *queue;

void setup()
{
	queue = new Queue<Task>;
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

