package com.iqb.consumer.mq.common.base;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.iqb.consumer.mq.common.exception.IqbMqException;
import com.iqb.consumer.mq.common.util.DateTools;

/**
 * 服务基类
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年1月21日-下午4:26:09
 */
@Component
@Service("baseServicePara")
public class BaseService {
	
	/**日志处理**/
	private static Logger log = LoggerFactory.getLogger(BaseService.class);
	
	/**
	 * 获取当先日期 格式YYYYMMDD	
	 * @return 
	 */
    protected String getTRXDATE(){
    	return DateTools.getTRXDATE();
    }
	/**
	 * 获取时分秒 HHMMSS
	 * @return
	 */
    protected String getTRXTIME(){
    	return DateTools.getTRXTIME();
    }
    
    /**
     * 获取20序列号  YYYYMMDDHHMMSS+6位随机数
     * @return
     */
    protected String getSeqNo(){
    	
	     String seqNo = DateTools.getYmdhmsTime();
	     Random random = new Random();
	     for(int i=0;i<6;i++){
	    	 seqNo+=random.nextInt(10);
		 }
	     return seqNo;	
    }
    
	/**
	 * 异常返回信息处理(新增和修改的处理)
	 * @param retNum 
	 * @param retCode
	 * @param info
	 * @throws Exception
	 */
	public void falseInfoThrows(int retNum,String retCode,String info)throws Exception{
		if(1!=retNum){
			log.error(info);
			throw new IqbMqException(retCode);
		}
	}
}