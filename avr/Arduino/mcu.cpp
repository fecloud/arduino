/*
 * mcu.cpp
 *
 *  Created on: 2014Äê3ÔÂ11ÈÕ
 *      Author: Feng OuYang
 */

#include "Arduino.h"

void setup(void)
{
	Serial.begin(9600);
}

void loop(void){

	Serial.print("123");
	delay(1000);
}


