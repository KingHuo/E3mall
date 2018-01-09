package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem getById(long id) throws Exception;
	
	DataGridResult getItemListDataGridResult(int page , int rows);
	
	E3Result saveItem(TbItem item,String desc);
	
	TbItemDesc getItemDescById(long id);
	
}
