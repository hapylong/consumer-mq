package com.iqb.consumer.mq.common.mq.thread;

import org.slf4j.LoggerFactory;

/**
 *   mq下游阻塞消息队列出队线程实现类
 * @author jack
 *
 */
public class ConsumerThread implements Runnable
{
	    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerThread.class);
	    
	    /**
	     * mq消费端统一接口
	     */
	    private IMqConsumer mqConsumer;
	    
	    /**
	     * 自定义 block
	     */
	    private QbBlockQueue myqueue;
	
	    /**
	     * 构造函数
	     * 
	     * @param thread_id
	     */
	    public ConsumerThread(int thread_id, QbBlockQueue myqueue,IMqConsumer mqConsumer)
	    {
	    	this.mqConsumer = mqConsumer;
	        this.myqueue = myqueue;
	        logger.info("任务处理线程 " + thread_id + " 创建成功");
	    }
	
	    public void run()
	    {
	    	try {	
	    		mqConsumer.execute(myqueue);   		
			} catch (Exception e) {
				logger.error("阻塞消息队列出队异常",e);
			}
	    }
}