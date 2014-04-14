// /*/*
//  * Queue.h
//  *
//  *  Created on: 2014��3��17��
//  *      Author: Feng OuYang
//  */

// #ifndef QUEUE_H_
// #define QUEUE_H_
// #ifdef __cplusplus
// extern "C"
// {
// #endif
// typedef void * Item;
// typedef struct node * PNode;
// typedef struct node
// {
// 	Item data;
// 	PNode next;
// } Node;

// typedef struct
// {
// 	PNode front;
// 	PNode rear;
// 	int size;
// } Queue;

// /*����һ���ն���*/
// Queue *InitQueue();

// /*����һ������*/
// void DestroyQueue(Queue *pqueue);

// /*���һ������*/
// void ClearQueue(Queue *pqueue);

// �ж϶����Ƿ�Ϊ��
// int IsEmpty(Queue *pqueue);

// /*���ض��д�С*/
// int GetSize(Queue *pqueue);

// /*���ض�ͷԪ��*/
// PNode GetFront(Queue *pqueue, Item *pitem);

// /*���ض�βԪ��*/
// PNode GetRear(Queue *pqueue, Item *pitem);

// /*����Ԫ�����*/
// PNode EnQueue(Queue *pqueue, Item item);

// /*��ͷԪ�س���*/
// PNode DeQueue(Queue *pqueue, Item *pitem);

// /*�������в��Ը����������visit����*/
// void QueueTraverse(Queue *pqueue, void (*visit)());
// #ifdef __cplusplus
// }
// #endif
// #endif /* QUEUE_H_ */
