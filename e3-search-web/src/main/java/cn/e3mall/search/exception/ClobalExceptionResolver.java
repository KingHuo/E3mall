package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


public class ClobalExceptionResolver implements HandlerExceptionResolver {

	Logger logger = LoggerFactory.getLogger(ClobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request	, HttpServletResponse response, Object handler,
			Exception e) {
		// 写日志
		logger.debug("进入debug 异常处理");
		logger.info("info 的异常处理");
		logger.error("系统发生异常",e);
		// 发邮件
		// 发短信
		// 展示一个友好错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "系统异常,请稍后重试");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
