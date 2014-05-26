
#include "core/Arduino.h"
//定义队列的节点结构
template <class T>
struct NODE
{
	NODE<T>* next;
	T data;
};

template <class T>
class Queue
{
public:
	Queue()
	{
		NODE<T>* p = new NODE<T>;
		if (NULL == p)
		{
			//cout << "Failed to malloc the node." << endl;
		}
		p->data = NULL;
		p->next = NULL;
		front = p;
		rear = p;
	}

//在队尾入队
	void push(T e)
	{
		NODE<T>* p = new NODE<T>;
		if (NULL == p)
		{
			//cout << "Failed to malloc the node." << endl;
		}
		p->data = e;
		p->next = NULL;
		rear->next = p;
		rear = p;
	}

//在队头出队
	T pop()
	{
		T e;

		if (front == rear)
		{
			//cout << "The queue is empty." << endl;
			return NULL;
		}
		else
		{
			NODE<T>* p = front->next;
			front->next = p->next;
			e = p->data;
			//注意判断当只有一个元素，且删除它之后，rear指向的node被删除
			//应将其指向头结点
			if (rear == p)
			{
				rear = front;
			}
			delete p; p = NULL;
			return e;
		}
	}

	//取得队头元素
	T front_element()
	{
		if (front == rear)
		{
			//cout << "The queue is empty." << endl;
			return NULL;
		}
		else
		{
			NODE<T>* p = front->next;
			return p->data;
		}
	}

	T back_element()
	{
		if (front == rear)
		{
			//cout << "The queue is empty." << endl;
			return NULL;
		}
		else
		{
			return rear->data;
		}
	}
	
	//取得队列元素个数
	int size()
	{
		int count(0);

		NODE<T>* p = front;

		while (p != rear)
		{
			p = p->next;
			count++;
		}
		return count;
	}
	
	//判断队列是否为空
	bool empty()
	{
		if (front == rear)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

private:
	NODE<T>* front; //指向头结点的指针。 front->next->data是队头第一个元素。
	NODE<T>* rear;//指向队尾（最后添加的一个元素）的指针
};

// int _tmain(int argc, _TCHAR* argv[])
// {
// 	MyQueue<int> myqueue;
// 	cout << myqueue.size() << endl;
// 	myqueue.push(10);
// 	myqueue.push(20);
// 	myqueue.push(30);
// 	cout << myqueue.front_element() << endl;
// 	cout << myqueue.back_element() << endl;
// 	myqueue.pop();
// 	if (myqueue.empty())
// 	{
// 		cout << "The queue is empty now." << endl;
// 	}
// 	else
// 	{
// 		cout << "The queue has " << myqueue.size() << " elements now." << endl;
// 	}
// 	myqueue.pop();
// 	myqueue.pop();
// 	if (myqueue.empty())
// 	{
// 		cout << "The queue is empty now." << endl;
// 	}
// 	else
// 	{
// 		cout << "The queue has " << myqueue.size() << " elements now." << endl;
// 	}
// 	return 0;
// }