package cn.e3mall.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMQQueue {

	@Test
	public void testQueueProducer() throws Exception {
		// 1）创建一个连接工厂对象。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 2）使用连接工厂对象创建一个Connection
		Connection connection = connectionFactory.createConnection();
		// 3）开启连接
		connection.start();
		// 4）使用Connection对象创建一个Session
		//第一个参数：是否开启事务。一般不开启
		//第二个参数：如果开启事务，第二参数无意义。如果不开启，消息的应答模式。手动应答、自动应答。可以是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5）创建一个消息的Destnation对象。一种是Queue一种是Topic，此处应该选择Queue
		Queue queue = session.createQueue("test-queue");
		// 6）使用Session对象创建一个消息的生产者，基于Destnation创建。
		MessageProducer producer = session.createProducer(queue);
		// 7）创建一个消息对象。TextMessage
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello activemq");*/
		TextMessage textMessage = session.createTextMessage("hello activemq");
		// 8）发送消息
		producer.send(textMessage);
		// 8）关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testQueueConsumer() throws Exception {
		// 1）创建一个connectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 2）使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 3）开启连接
		connection.start();
		// 4）创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5）创建一个Destnation对象
		Queue queue = session.createQueue("test-queue");
		// 6）创建一个消息的消费者。
		MessageConsumer consumer = session.createConsumer(queue);
		// 7）设置监听器
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// 8）接收消息
				TextMessage textMessage = (TextMessage) message;
				// 9）打印消息
				try {
					System.out.println("接收到消息：");
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		//系统等待
		System.out.println("等待接收消息。。。。");
		System.in.read();
		System.out.println("系统关闭。。。");
		// 10）关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
