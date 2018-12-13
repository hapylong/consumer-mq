package com.iqb.consumer.mq.asset.allocation.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.iqb.consumer.mq.common.mongo.MongoDBUtils;
import com.iqb.consumer.mq.common.util.MongoCollectionsUtil;

@Repository
public class MongoDBDao {
	
	 private final static Logger logger = LoggerFactory.getLogger(MongoDBDao.class);
	 
	 @Autowired
	 private MongoDBUtils mongoDBUtils;
	 /**
	  * Description 保存资金推送log
	  * @param obj
	  */
	 public void saveAssetLog(Object obj) {
	        try {
	            logger.info("记录mongo日志:{}", JSONObject.toJSONString(obj));
	        	mongoDBUtils.save(obj, MongoCollectionsUtil.ASSET_MONGO_COLLECTION_NAME);
	        } catch (Exception e) {
	            logger.error("mongo记录资金推送异常：", e);
	        }
	    }
}
