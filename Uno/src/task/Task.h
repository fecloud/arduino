/*
 * Task.h
 *
 *  Created on: 2014年4月17日
 *      Author: Feng OuYang
 */

#ifndef TASK_H_
#define TASK_H_

class Task
{
public:
	virtual ~Task() =0 ;		
	virtual void doTask() =0 ;
};

#endif /* TASK_H_ */
