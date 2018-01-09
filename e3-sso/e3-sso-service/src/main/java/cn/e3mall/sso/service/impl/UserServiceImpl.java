package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public E3Result CheckData(String data, int type) {
		// 判断数据类型 1 表示用户名 2：手机号 3：邮箱
		// 2、根据不同的数据类型创建不同的查询条件。
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(data);
		} else if (type == 3) {
			criteria.andEmailEqualTo(data);
		} else {
			return E3Result.build(400, "数据类型不正确");
		}
		// 执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 5、如果有数据返回false
		// 6、如果没有数据发返回true
		if (list != null && list.size() > 0) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);

	}

	@Override
	public E3Result userRegister(TbUser tbUser) {
		// 1、数据有效性校验
		if (StringUtils.isBlank(tbUser.getUsername())) {
			return E3Result.build(400, "用户名不能为空");
		}
		if (StringUtils.isBlank(tbUser.getPassword())) {
			return E3Result.build(400, "密码不能为空");
		}
		// 判断用户名是否可用
		E3Result e3Result = CheckData(tbUser.getUsername(), 1);
		if (!(boolean) e3Result.getData()) {
			return E3Result.build(400, "用户名已存在");
		}
		// 手机号判断
		if (StringUtils.isNotBlank(tbUser.getPhone())) {
			e3Result = CheckData(tbUser.getPhone(), 2);
			if (!(boolean) e3Result.getData()) {
				return E3Result.build(400, "手机号已注册");
			}
		}
		// 邮箱判断
		if (StringUtils.isNoneBlank(tbUser.getEmail())) {
			e3Result = CheckData(tbUser.getEmail(), 3);
			if (!(boolean) e3Result.getData()) {
				return E3Result.build(400, "该邮箱已注册");
			}
		}
		// 2、密码进行md5加密   封装表单数据
		String md5Pwd = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Pwd);
        tbUser.setUpdated(new Date());
        tbUser.setCreated(new Date());
		// 3、插入数据
        tbUserMapper.insert(tbUser);
		// 4、返回成功
		return E3Result.ok();
	}

	@Override
	public E3Result login(String username, String password) {
		// 1、根据用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 2、如果没有查询到数据，返回登录失败
		if(list==null||list.size()==0){
			return E3Result.build(400, "用户名或密码错误");
		}
		// 3、如果查询到数据判断密码是否正确，需要进行md5加密，然后再比对。
		TbUser tbUser = list.get(0);
		if(tbUser.getPassword().equals(DigestUtils.md5Digest(password.getBytes()))){
			// 4、如果密码不正确登录失败
			return E3Result.build(400, "用户名 或 密码错误！");
		}
		// 5、如果密码正确登录成功
		String token = UUID.randomUUID().toString();
		// 6、把用户信息保存到Session
			// 1）生成token
			// 2）把用户信息保存到redis
			// 3）设置token过期时间
			// 4）在表现层把token写入cookie
		// 7、返回E3Result，其中包含token
		return null;
	}

}
