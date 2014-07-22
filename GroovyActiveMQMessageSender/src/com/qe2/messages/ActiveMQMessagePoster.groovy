package com.qe2.messages;

// Posts a message to a ActiveMQ Queue
// Author. NK
//http://localhost:8161/admin/queues.jsp
import javax.jms.DeliveryMode
import javax.jms.Session
import org.apache.activemq.ActiveMQConnection
import org.apache.activemq.ActiveMQConnectionFactory

class ActiveMQMessagePoster
{

	private def username = ActiveMQConnection.DEFAULT_USER
	private def password = ActiveMQConnection.DEFAULT_PASSWORD
	def url = ActiveMQConnection.DEFAULT_BROKER_URL   //	private def url = 'tcp://localhost:8161'

	void post(textMessage,subject)
	{
		def connection = null
		try
		{
			println("Connecting to [$url]")
			println("Publishing to queue [$subject]")

			// Create the connection.
			def connectionFactory = new ActiveMQConnectionFactory(username, password, url)
			connection = connectionFactory.createConnection()
			connection.start()

			// Create the session
			def session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE)
			def destination = session.createQueue(subject)

			// Create the producer.
			def producer = session.createProducer(destination)
			producer.setDeliveryMode(DeliveryMode.PERSISTENT)

			// Create the message
			def message = session.createTextMessage(textMessage)

			println("Sending message: $message.text")

			// Publish the message
			producer.send(message)

			// Commit
			session.commit()
		}
		catch (Exception e)
		{
			// Send to integration errors
			e.printStackTrace()
		}
		finally
		{
			// Close the connection and exit
			try
			{
				connection.close()
			}
			catch (Throwable e)
			{
				// Swallow
			}
		}
	}

}
