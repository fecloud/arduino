/*
 * uno.h
 *
 *  Created on: 2014Äê3ÔÂ22ÈÕ
 *      Author: braver
 */

#ifndef UNO_H_
#define UNO_H_
#include "Queue.h"

int conneted_server();
#ifdef __cplusplus
extern "C"
{
#endif
void invoke(void (*in)());

#ifdef __cplusplus
}
#endif

#endif /* UNO_H_ */
