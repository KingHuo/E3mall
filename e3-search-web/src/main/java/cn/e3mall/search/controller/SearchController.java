package cn.e3mall.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Controller
public class SearchController {

	@Value("${search.result.rows}")
	private int ROWS;
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(Model model, String keyword,@RequestParam(defaultValue="1")int page) throws Exception{
		if(StringUtils.isNotBlank(keyword)){
			keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
		}
		SearchResult searchResult = searchService.serach(keyword, page, ROWS);
		//查询结果返回给jsp
		model.addAttribute("totalPages",searchResult.getPageCount());
		model.addAttribute("recourdCount",searchResult.getRecourdCount());
		model.addAttribute("itemList",searchResult.getItemList());
		//查询参数的回显
		model.addAttribute("page",page);
		model.addAttribute("query",keyword);
		return "search";
	}
	
}
