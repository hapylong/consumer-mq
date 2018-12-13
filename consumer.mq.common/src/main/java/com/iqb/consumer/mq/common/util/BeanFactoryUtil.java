package com.iqb.consumer.mq.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * spring xml init
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年1月21日-下午4:22:16
 */
public class BeanFactoryUtil {
	
private static ApplicationContext ctx_producer = null;
	
	public static void init() {
		if(ctx_producer == null) {
			ctx_producer = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-config.xml","classpath:/config/mq/spring-init.xml"});
		}
	}
	
	public static ApplicationContext getContext() {
		if(ctx_producer == null) {
			init();
		}
		return ctx_producer;
	}
}
