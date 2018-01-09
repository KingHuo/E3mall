package cn.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MymessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
		TextMessage message2 = (TextMessage) message;
		try {
			System.out.println(message2.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
