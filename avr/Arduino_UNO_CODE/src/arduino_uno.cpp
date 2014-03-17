#include "Arduino.h"

int num;

void setup(void) {
	Serial.begin(9600);
}

void loop(void) {

	if (Serial.available()) {
		num = Serial.read();
		Serial.println(num);

	}

}
