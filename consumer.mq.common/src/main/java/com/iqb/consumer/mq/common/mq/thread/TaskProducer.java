package com.iqb.consumer.mq.common.mq.thread;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.iqb.consumer.mq.common.mq.queue.Queue;


/**
 * 阻塞消息队列管理类 
 * @author jack
 *
 */
@Component
public class TaskProducer
{    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TaskProducer.class);
    
    /**
     * 注入queue
     */
    private static Queue queue;
    
    /**
     * 注入线程池
     */
    private static Consumer consumer;

 
    private static void startConsumer()
    {
        
        logger.info("开始启动Consumer线程池");
        
        //处理线程池
        consumer.startConsume();

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ie)
        {
            logger.error("等待线程池启动时发生错误", ie);
        }
    }

    @Autowired(required = true)
	public void setQueue(@Qualifier("queueffjfout") Queue queue) {
		TaskProducer.queue = queue;
	}


    @Autowired(required = true)
	public void setConsumer(@Qualifier("outputpoollic") Consumer consumer) {
		TaskProducer.consumer = consumer;
		 startConsumer();
	}  
    
    
    /**
     * 入口
     * @param msg
     */
    public void addRedisRecord(String msg)
    {
        logger.debug("开始加入消息到队列:"+msg);
        queue.put(msg);
    }
}