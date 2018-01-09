package cn.e3mall.solrJ.cloud;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testSolrCloudAddDocument() throws Exception{
		// 1. 创建一个SolrServer对象,应该使用CloudSolrServer
		// 2. CloudSolrServer的参数,zkHost,zookeeper的地址列表
		// 3. 需要指定使用哪一个Collection.需要设置defaultCollection属性
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2185,192.168.25.128:2183,192.168.25.128:2184");
		cloudSolrServer.setDefaultCollection("collection2");
		// 4. 创建一个SolrInputDocumentduixiang
		SolrInputDocument document = new SolrInputDocument();
		// 5. 向文档对象中添加域
		document.addField("id", "100");
		document.addField("item_title", "测试title");
		document.addField("item_price", 100);
		document.addField("item_image", "测试图片");
		// 6. 把文档对象写入索引库
		cloudSolrServer.add(document);
		// 7. 提交
		cloudSolrServer.commit();
	}
	
}
