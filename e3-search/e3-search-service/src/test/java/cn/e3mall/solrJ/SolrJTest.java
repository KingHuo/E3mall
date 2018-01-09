package cn.e3mall.solrJ;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	/*@Test
	public void addDocument() throws Exception {
		// 1、把solrJ的jar包添加到工程。
		// 2、创建一个SolrServer对象。创建一个和sorl服务的连接。HttpSolrServer。
		//如果不带Collection默认连接Collection1
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8100/solr");
		// 3、创建一个文档对象。SolrInputDocument。
		SolrInputDocument document = new SolrInputDocument();
		// 4、向文档对象中添加域。必须有一个id域。而且文档中使用的域必须在schema.xml中定义。
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		// 5、把文档添加到索引库
		solrServer.add(document);
		// 6、Commit。
		solrServer.commit();
	}

	@Test
	public void searchItem() throws Exception{
		//1.创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8100/solr");
		//2.创建一个查询对象，可以参考solr的后台的查询功能设置条件
		SolrQuery solrQuery = new SolrQuery();
		//3.设置默认搜索域
		solrQuery.set("df", "item_title");
		//4.设置查询条件
		solrQuery.setQuery("手机");
		
		//5.设置分页条件
		solrQuery.setStart(1);
		solrQuery.setRows(5);
		//6.开启高亮
		solrQuery.setHighlight(true);
		//6.1设置高亮显示的域
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		//7. 执行查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//7.1 取查询结果
		SolrDocumentList results = queryResponse.getResults();
		//7.1.2 查询结果的总记录数
		System.out.println("查询总结记录数: "+results.getNumFound());
		//7.2 取高亮
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//9. 取商品列表
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list!=null&&list.size()>0){
				//取高亮后结果
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	*/
	
}
