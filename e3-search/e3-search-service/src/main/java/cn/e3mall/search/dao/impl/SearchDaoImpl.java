package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
		// 执行查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 总记录数
		long numFound = solrDocumentList.getNumFound();
		// 取高亮
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		// 接收
		List<SearchItem> itemList = new ArrayList<>();
		// 取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			searchItem.setTitle(title);
			// 添加商品列表
			itemList.add(searchItem);
		}
		// 封装到SearchResult对象中
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecourdCount(numFound);
		// 返回结果
		return result;
	}

}
