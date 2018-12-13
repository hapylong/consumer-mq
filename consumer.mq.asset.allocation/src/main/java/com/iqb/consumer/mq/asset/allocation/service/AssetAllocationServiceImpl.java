package com.iqb.consumer.mq.asset.allocation.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.consumer.mq.asset.allocation.biz.AssetAllocationBiz;
import com.iqb.consumer.mq.common.mq.thread.IMqConsumer;
import com.iqb.consumer.mq.common.mq.thread.QbBlockQueue;
import com.iqb.consumer.mq.common.util.BeanFactoryUtil;

/**
 * 
 * Description: 资产分配实现类
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"rawtypes"})
public class AssetAllocationServiceImpl implements IMqConsumer{

    private static Logger logger = LoggerFactory.getLogger(AssetAllocationServiceImpl.class);
    
    /**
     * 资产分配biz服务
     */
    private AssetAllocationBiz assetAllocationBiz;
    
    public void execute(QbBlockQueue queue) {
        
        Map beanM = null;     
        try {
            assetAllocationBiz = (AssetAllocationBiz)BeanFactoryUtil.getContext().getBean("assetAllocationBiz");             
            beanM = queue.ConverToObject();  
            /** 处理资产分配消息 **/
            assetAllocationBiz.dealAssetAllocationMsg(beanM);
            
        } catch (Exception e) {
            logger.error("资产分配消息 - 阻塞消息队列消费异常"+JSON.toJSONString(beanM),e);
        }       
    }
}
