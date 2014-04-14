// #include "Arduino.h"
// #include <Ethernet.h>
// #include <SPI.h>
// #include "WebSocketClient.h"
// #include "uno.h"

// byte mac[] =
// { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
// const char *server = "10.0.0.2";
// WebSocketClient client;
// Queue *pq;

// // void invoke(void (*in)())
// // {
// // 	(*in)();
// // }

// void dataArrived(WebSocketClient client, String data)
// {
// 	Serial.println("Data Arrived: " + data);
// 	if(data.startsWith("led")){
// 		Serial.println("led cmd");
// 		String v = data.substring(4);
// 		Serial.println(v);
// 		if(v=="1"){
// 			Serial.println("led HIGH");
// 			digitalWrite(2,HIGH);
// 		}else {
// 			Serial.println("led LOW");
// 			digitalWrite(2,LOW);
// 		}
// 	}
// 	client.send("ok");
// }

// int conneted_server()
// {
// 	pinMode(2,OUTPUT);
// 	digitalWrite(2,LOW);
// 	Ethernet.begin(mac);
// 	client.connect(server,"arduino uno",9000);
// 	client.setDataArrivedDelegate(dataArrived);
// 	return 1;
// }

// void setup()
// {
// 	Serial.begin(9600);
// 	conneted_server();
// 	// pq = InitQueue();
// 	//EnQueue(pq, (void*) conneted_server);
// }

// void loop()
// {
// //	if (!IsEmpty(pq))
// //	{
// //		Item item;
// //		DeQueue(pq, &item);
// //
// //		invoke(item);
// //	}

// 	client.monitor();
// }

