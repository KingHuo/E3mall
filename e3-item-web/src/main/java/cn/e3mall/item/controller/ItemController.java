package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String ItemInfo(@PathVariable Long itemId,Model model) throws Exception{
		//商品基本信息
		TbItem tbItem = itemService.getById(itemId);
		Item item = new Item(tbItem);
		//商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		//把数据传递给jsp
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
	
}
