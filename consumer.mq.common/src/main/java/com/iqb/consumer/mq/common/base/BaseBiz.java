package com.iqb.consumer.mq.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.iqb.consumer.mq.common.db.SubDbContextHolder;


/**
 * biz基础类
 * @author jack
 *
 */
@Component
public class BaseBiz {
	
	protected final String MASTER = "mdb";//主库标示
	protected final String SLAVE = "sdb";//从库标示
	
	/**
	 * 数据源设置注入
	 */
	@Autowired
	@Qualifier("common_consumer_subDbContextHolder")
	private SubDbContextHolder subDbContextHolder;
	
	 /**
     * 设置db实例
     * @param idNum
     * @param dbType
     */
	public void setDb(int idNum,String dbType){	    	
    	if(MASTER.equals(dbType)){
    		subDbContextHolder.setMaster(idNum);
    	}else if(SLAVE.equals(dbType)){
    		subDbContextHolder.setSlave(idNum);
    	}
	}
}
