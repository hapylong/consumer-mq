package com.iqb.consumer.mq.common.mq.thread;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iqb.consumer.mq.common.mq.queue.AbstractTaskQueue;
import com.iqb.consumer.mq.common.util.JSONUtil;



/**
 * 自定义 block
 * @author jack
 *
 */
public class QbBlockQueue {
	
	private static Logger log = LoggerFactory.getLogger(QbBlockQueue.class);
		
    /**
     * 阻塞消息队列
     */
    private AbstractTaskQueue queue;
    
	public QbBlockQueue(AbstractTaskQueue queue){
    	this.queue = queue;
    }
	
	/**
	 * 消息jsonstring转化为object
	 * @param classPathStr  IMqConsumer实现类完整路径
	 * @return  mqInfoObject  mq消息对象
	 */
	public  Map<String, Object> ConverToObject(){
		
		String queueStr = null;
		Map<String, Object> mqInfoObject = null;
		try {
		    queueStr =  queue.take();
			mqInfoObject = JSONUtil.toMap(queueStr);
		} catch (Exception e) {
			log.error("mq下游阻塞消息队列出队消息类型转换异常：", e);
		} 
		return mqInfoObject;
	}
}