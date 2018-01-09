package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {

	@Value("${picture.server.url}")
	private String pictureServerUrl;
	
	//produces属性：指定返回结果的contentType。前提是返回的必须是字符串。
	//@RequestMapping(value="/pic/upload",produces="text/plain;charset=utf-8")
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			// 1）接收上传的文件，取文件原始名称
			String filename = uploadFile.getOriginalFilename();
			// 2）从原始名称中截取文件扩展名
			String extName = filename.substring(filename.lastIndexOf(".") + 1);
			// 3）使用FastDFSClient工具类上传文件。
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:/conf/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			// 4）需要把url拼接成完整的url
			url = pictureServerUrl + url;
			// 5）创建一个map对象，设置返回内容
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			// 6）返回Map对象
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "File upload failed");
			return JsonUtils.objectToJson(result);
		}
	}

}
