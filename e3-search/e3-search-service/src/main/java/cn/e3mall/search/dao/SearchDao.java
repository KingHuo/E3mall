package cn.e3mall.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import cn.e3mall.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery solrQuery) throws SolrServerException;
	
}
