package com.iqb.consumer.mq.common.mq.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.iqb.consumer.mq.common.mq.queue.AbstractTaskQueue;


/**
 * 阻塞消息队列入队线程池管理
 * 
 * @version 1.0
 */
public class Consumer extends AbstractTaskConsumer
{
	 private static org.slf4j.Logger logger = LoggerFactory.getLogger(Consumer.class);

     private ScheduledExecutorService executorService;	  
     
     /**
      * 阻塞消息队列 
      */
     private  AbstractTaskQueue queue;
    
     /**
      * 阻塞消息队列消费端统一接口
      */
	 private IMqConsumer mqConsumer;
	    
     /**
     * 初始化mq消费端接口实现类
     */
	 public void initMqImplClass(){   			
		try {
			mqConsumer = (IMqConsumer) Class.forName(super.thread_queue_out_implclass).newInstance();
		} catch (Exception e) {
			logger.error("动态创建" + super.thread_queue_out_implclass + "异常",e);
		} 
     }	
	    
	    /**
	     * 启动消息Consumer的线程池
	     * 
	     * @param message
	     */
	    public void startConsume()
	    {
	        logger.info("开始启动消息处理线程池");
	        initMqImplClass();
	        
	        if (this.executorService == null || executorService.isShutdown()
	                || executorService.isTerminated())
	        {
	            executorService = Executors.newScheduledThreadPool(super.consumer_thread_number);
	
	            for (int i = 1; i < (super.consumer_thread_number + 1); i++)
	            {
	                // 此处initialDelay是i，单位秒
	            	executorService.scheduleAtFixedRate(new ConsumerThread(i,  new QbBlockQueue(queue),mqConsumer), 0, interval,TimeUnit.SECONDS);
	            }
	        }       
	        logger.info("消息处理线程池初始化成功, 共含"+super.consumer_thread_number+"线程, 每"+interval+"秒钟轮询队列一次");
	    }
	    
	    /**
	     * @return the executorService
	     */
	    public ScheduledExecutorService getExecutorService()
	    {
	        return executorService;
	    }
	
	    /**
	     * Spring注入queue
	     * @param queue the queue to set
	     */
	    public void setQueue(AbstractTaskQueue queue)
	    {
	        this.queue = queue;
	    }  
}