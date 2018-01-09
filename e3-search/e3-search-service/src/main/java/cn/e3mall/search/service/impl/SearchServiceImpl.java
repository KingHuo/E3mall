package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao SearchDao;
	public SearchResult serach(String keyword, int page, int rows) throws Exception {
		// 1）创建一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		// 2）设置查询条件，可以参考solr的后台。
		solrQuery.setQuery(keyword);
		int start = (page -1 )*rows;
		solrQuery.setStart(start);
		solrQuery.setRows(rows);
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		// 3）调用Dao执行查询
		SearchResult searchResult = SearchDao.search(solrQuery);
		// 4）从结果中取总记录数
		long recourdCount = searchResult.getRecourdCount();
		// 5）计算查询结果的总页数。
		long pageCount = recourdCount / rows;
		if(recourdCount % rows > 0){
			pageCount++;
		}
		// 6）把总页数添加到SearchResult中。
		searchResult.setPageCount(pageCount);
		// 7）返回SearchResult对象
		return searchResult;
	}

}
