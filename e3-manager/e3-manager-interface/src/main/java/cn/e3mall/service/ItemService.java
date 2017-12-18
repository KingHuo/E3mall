package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;

public interface ItemService {
	
	TbItem getById(long id) throws Exception;
	
}
