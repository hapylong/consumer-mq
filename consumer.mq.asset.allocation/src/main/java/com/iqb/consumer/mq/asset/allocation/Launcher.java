package com.iqb.consumer.mq.asset.allocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iqb.consumer.mq.asset.allocation.service.AssetAllocationService;
import com.iqb.consumer.mq.common.util.BeanFactoryUtil;
import com.iqb.consumer.mq.common.util.DateTools;

/**
 * 
 * Description: 资产分配消费包
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class Launcher {
	
	/** 日志类 **/
	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
	
	
	public static void main(String[] args) {
		logger.debug("******************* consumer.mq.asset.allocation  start up: " + DateTools.getYYYYMMDDMMHHSS() + " *******************");
		try{
			//初始化spring
			BeanFactoryUtil.init();
			
			AssetAllocationService assetAllocationService = (AssetAllocationService) BeanFactoryUtil.getContext().getBean("assetAllocationService");
			assetAllocationService.listenQueueMessages();
		}catch(Exception e){
			logger.error("******************* consumer.mq.asset.allocation  异常结束 *******************", e);
		}
		
	}

}
