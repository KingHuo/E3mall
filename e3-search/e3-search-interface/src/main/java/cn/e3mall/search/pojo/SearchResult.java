package cn.e3mall.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{

	private List<SearchItem> itemList;
	private long recourdCount;
	private long pageCount;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public long getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(long recourdCount) {
		this.recourdCount = recourdCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
}
