package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

public interface UserService {

	E3Result CheckData(String data , int type);

	E3Result userRegister(TbUser tbUser);
	
	E3Result login(String username,String password);
	
}
