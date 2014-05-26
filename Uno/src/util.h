/*
 * util.h
 *
 *  Created on: May 22, 2014
 *      Author: ouyangfeng
 */

#ifndef UTIL_H_
#define UTIL_H_
#include "core/Arduino.h"
#define DEBUG

#ifdef DEBUG
#define debug(x) Serial.println(x);
#else
#define debug(x)
#endif


#endif /* UTIL_H_ */
