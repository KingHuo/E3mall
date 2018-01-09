package cn.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public final class HTMLGenMessageListener implements MessageListener {

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${html.gen.path}")
	private String htmlGenPath;
	public void onMessage(Message message) {
		try {
		// 1、创建一个MessageListener实现类
		// 2、把实现类中接收消息
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		// 3、从消息中取商品id
		long parseLong = Long.parseLong(text);
		Thread.sleep(1000);
		// 4、根据商品id查询商品基本信息和商品描述。
		TbItem tbItem = itemService.getById(parseLong);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemService.getItemDescById(parseLong);
		// 5、创建一个商品详情页面的freemarker模板
		Map data = new HashMap<>();
		data.put("item", item);
		data.put("itemDesc", itemDesc);
		// 6、指定文件的输出目录
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		// 7、生成文件。文件名是商品id。路径任意目录，不要生成在工程内部。
		Writer writer = new FileWriter(new File(htmlGenPath+parseLong+".html"));
		template.process(data, writer);
		// 8、关闭流
		writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
