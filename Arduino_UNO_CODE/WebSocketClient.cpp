/*
 WebsocketClient, a websocket client for Arduino
 Copyright 2011 Kevin Rohling
 http://kevinrohling.com
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

#include "WebSocketClient.h"
#include <WString.h>
#include <string.h>
#include <stdlib.h>

bool WebSocketClient::connect(const char *hostname , char name[], int port)
{
	bool result = false;

	if (_client.connect(hostname, port))
	{
		sendHandshake(name);
		result = readHandshake();
	}

	return result;
}

bool WebSocketClient::connected()
{
	return _client.connected();
}

void WebSocketClient::disconnect()
{
	_client.stop();
}

void WebSocketClient::monitor()
{
	char character;
	if (_client.available() > 0)
	{
		character = _client.read();
		if (character != '\n' && character != '\r')
		{
			bufferLine += character;
		}
		else if (character != '\r' && _dataArrivedDelegate != NULL)
		{
			_dataArrivedDelegate(*this, bufferLine);
			bufferLine = "";
		}
	}
}

void WebSocketClient::setDataArrivedDelegate(
		DataArrivedDelegate dataArrivedDelegate)
{
	_dataArrivedDelegate = dataArrivedDelegate;
}

void WebSocketClient::sendHandshake(char  name[])
{
	_client.print("hand:");
	_client.print(name);
	_client.println();
}

bool WebSocketClient::readHandshake()
{
	bool result = false;
	String handshake = "", line;
	int maxAttempts = 300, attempts = 0;

	while (_client.available() == 0 && attempts < maxAttempts)
	{
		delay(100);
		attempts++;
	}

	while ((line = readLine()) != "")
	{
		handshake += line;
	}

	result = handshake.equals("ok");
	Serial.println(result);
	if (!result)
	{
		_client.stop();
	}

	return result;
}

String WebSocketClient::readLine()
{
	String line = "";
	char character;

	while (_client.available() > 0 && (character = _client.read()) != '\n')
	{
		if (character != '\r' && character != -1)
		{
			line += character;
		}
	}

	return line;
}

void WebSocketClient::send(String data)
{
	_client.print(data);
	_client.println();
	_client.flush();
}

