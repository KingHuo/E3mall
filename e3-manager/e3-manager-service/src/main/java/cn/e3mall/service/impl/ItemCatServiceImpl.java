package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemCatService;


@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<TreeNode> getItemCatList(long parentId) {
		// 1） 根据parentId查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 2） 把TbItemCat列表转换成TreeNode的列表
		List<TreeNode> TreeNodeList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			TreeNode node = new TreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			//如果存在子节点 closed    没有返回open
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//把note添加到列表
			TreeNodeList.add(node);
		}
		// 3） 返回结果
		return TreeNodeList;
	}

}
