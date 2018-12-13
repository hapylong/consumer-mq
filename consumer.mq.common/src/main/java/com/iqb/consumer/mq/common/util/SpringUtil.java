package com.iqb.consumer.mq.common.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
/**
 * 
 * @author jack
 * spring工具类
 * 该类需要web启动器加载
 */
public class SpringUtil {
	
	private SpringUtil() {
	}
	private static SpringUtil springUtil = new SpringUtil();
	
	public static SpringUtil getInstance(){
		return springUtil;
	}
	
	/** spring context**/
	private   AnnotationConfigWebApplicationContext webApplicationContext;

	public AnnotationConfigWebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public void setWebApplicationContext(
			AnnotationConfigWebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}
	

	
	/** spring context**/
	private   ClassPathXmlApplicationContext context;

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void setContext(ClassPathXmlApplicationContext context) {
		this.context = context;
	}
 
}
