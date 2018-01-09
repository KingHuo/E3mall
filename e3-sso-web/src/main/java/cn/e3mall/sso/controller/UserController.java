package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.UserService;
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping("/user/check/{data}/{type}")
	//返回json数据
	@ResponseBody
	public E3Result checkUser(@PathVariable String data,@PathVariable int type){
		E3Result e3Result = userService.CheckData(data, type);
		return e3Result;
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public E3Result userRegister(TbUser tbUser){
		E3Result e3Result = userService.userRegister(tbUser);
		return e3Result;
	}
	
	@RequestMapping("/page/login")
	public String showLogin(){
		return "login";
	}
	
	
	
}
