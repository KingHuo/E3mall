package cn.e3mall.item.controller;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class GenHtmlTestController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/html/gen")
	@ResponseBody
	public String GenHtml() throws Exception{
		//注入一个freemarkerConfig对象  获得Configuration对象
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//加载模板
		Template template = configuration.getTemplate("hello2.ftl");
		//创建一个数据集
		Map data = new HashMap<>();
		data.put("hello", "spring 整合 freemarker");
		//指定文件的输出路径及文件名
		Writer writer = new FileWriter("C:/Users/kangkang/Desktop/first/hello.html");
		//生成文件
		template.process(data, writer);
		//关闭流
		writer.close();
		return "ok";
	}
	
}
