package cn.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.mapper.SearchItemMapper;
import cn.e3mall.search.pojo.SearchItem;

public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try {
			// 1、接收消息
			TextMessage textMessage = (TextMessage) message;
			// 2、从消息中取商品id
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			Thread.sleep(3000);
			// 3、根据商品id取商品信息
			SearchItem searchItem = searchItemMapper.getSearchItemById(itemId);
			// 4、把商品信息写入索引库
			//创建SolrInputDocument对象
			SolrInputDocument document = new SolrInputDocument();
			//向文档对象中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//把文档对象写入索引库(需要 SolrServer [添加相关jar包
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
