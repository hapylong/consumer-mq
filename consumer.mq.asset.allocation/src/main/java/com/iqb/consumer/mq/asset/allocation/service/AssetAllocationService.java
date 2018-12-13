package com.iqb.consumer.mq.asset.allocation.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqb.consumer.mq.asset.allocation.biz.AssetAllocationBiz;
import com.iqb.consumer.mq.common.mq.thread.TaskProducer;
import com.iqb.consumer.mq.common.util.CommonConst;

/**
 * Description: 资产分配service
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年10月17日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@SuppressWarnings({ "rawtypes" })
@Service
public class AssetAllocationService{

    /** 初始化日志类 **/
    private static Logger logger = LoggerFactory.getLogger(AssetAllocationService.class);

    /**
     * 注入阻塞消息队列管理类
     */
    @Autowired
    private TaskProducer taskProducer;

    @Autowired
    private AssetAllocationBiz assetAllocationBiz;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Description: 监听资产分配消息队列
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年10月17日 下午2:16:55
     */
    public void listenQueueMessages(){
        
        Map beanM = null;
        for(;;){
            try {
                Thread.sleep(50);
                beanM = assetAllocationBiz.listenAssetAllocat();
                if(null == beanM){
                    continue;
                }   
                
                /**
                 * 资产分配队列.
                 */
                Object msgType = beanM.get(CommonConst.QUEUE_MSG_TYPE);
                if(!msgType.equals(CommonConst.QUEUE_ASSET_ALLOCATION)){
                    logger.debug("监听到资产分配队列消息记录：" + JSON.toJSONString(beanM));
                    continue;
                }
                logger.info("已监听到资产分配队列消息记录：" + JSON.toJSONString(beanM));
                
                //放入阻塞消息队列
                taskProducer.addRedisRecord(mapper.writeValueAsString(beanM));

            }catch (Exception e) {
                e.printStackTrace();
                logger.error("资产分配队列消息处理异常 :" + JSON.toJSONString(beanM), e);
            }
        }
    }
}
    
