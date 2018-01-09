package cn.e3mall.pagetest;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class PageHelperTest {

	@Test
	public void testPageHelper() throws Exception {
		//设置分页信息
		PageHelper.startPage(1, 10);
		//执行查询
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		/*PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(list.size());*/
		Page page = (Page) list;
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(list.size());
		
	}
}
