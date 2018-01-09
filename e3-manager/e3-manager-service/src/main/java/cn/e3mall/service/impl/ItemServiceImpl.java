package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper; 
    @Autowired
    private TbItemDescMapper descMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
    @Resource
    private Destination itemAddTopic;
    @Autowired
    private JedisClient jedisClient;
    @Value("${item_expire_time}")
    private int item_time_out;
    
    
	public TbItem getById(long id) throws Exception {
		//查询缓存
		//如果缓存中没有就查询数据库
		String json = jedisClient.get("ITEM_INFO:"+id+":BASE");
		if(StringUtils.isNotBlank(json)){
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			return item;
		}
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		if(tbItem != null){
			//添加缓存
			jedisClient.set("ITEM_INFO:"+id+":BASE", JsonUtils.objectToJson(tbItem));
			//设置缓存过期时间
			jedisClient.expire("ITEM_INFO:"+id+":BASE", item_time_out);
		}
		return tbItem;
	}

	@Override
	public DataGridResult getItemListDataGridResult(int page, int rows) {
		// 1、设置分页信息
		PageHelper.startPage(page, rows);
		// 2、执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 3、取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		// 4、返回DataGridResult对象   page继承了ArrayList   ArrayList 实现了list
		DataGridResult result = new  DataGridResult();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}

	@Override
	public E3Result saveItem(TbItem item, String desc) {
		// 1）生成商品id
		final long itemId = IDUtils.genItemId();
		// 2）补全TbItem对象的属性
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 3）向商品表中插入商品信息。
		itemMapper.insert(item);
		// 4）创建TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5）补全pojo对象的属性
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		// 6）向商品描述表插入数据
		descMapper.insert(itemDesc);
		//发送商品添加消息
		jmsTemplate.send(itemAddTopic, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//返回商品id
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		// 7）返回E3Result，OK
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long id) {
		String itemDescJson = jedisClient.get("ITEM_INFO:"+id+":DESC");
		if(StringUtils.isNotBlank(itemDescJson)){
			TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(itemDescJson, TbItemDesc.class);
			return tbItemDesc;
		}
		TbItemDesc itemDesc = descMapper.selectByPrimaryKey(id);
		//将商品描述添加到缓存
		if(itemDesc != null){
			jedisClient.set("ITEM_INFO:"+id+":DESC", JsonUtils.objectToJson(itemDesc));
			jedisClient.expire("ITEM_INFO:"+id+":DESC", item_time_out);
		}
		return itemDesc;
	}

}
