package com.qe2.messages

/*
 * NK 21072014 
 * Demo for sending activeMQ messaging 
 * Constructing xml using groovy MarkupBuilder.
 * This is the startup/main class
 */
class MessageSenderMain {			
	static main(args) {
		MessageSender messageSender = new MessageSender();
		messageSender.sendMessage();
	}
}
