package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpringActiveMq {

	@Test
	public void testActiveMQSpring() throws Exception {
		// 1. 初始化一个Spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		// 2. 从容器中获得JMSTemplate对象
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 3. 从容器中获得一个Destination对象
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		// 4. 发送消息 JMSTemplate需要得到destination参数
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("spring activeMQ。。。。");
				return textMessage;
			}
		});

	}

}
