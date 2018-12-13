package com.iqb.consumer.mq.common.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Component
public class MongoDBUtils {

	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * Description:保存
	 * @param objectToSave
	 * @param collectionName
	 */
	public void save(Object objectToSave, String collectionName) {
		mongoTemplate.save(objectToSave, collectionName);
	}
	/**
	 * 查询
	 * @param collectionName
	 */
	public void query(String collectionName) {
		DBCollection userCollection = mongoTemplate.getCollection(collectionName);
		DBCursor cursor = userCollection.find();
		while (cursor.hasNext()) {
//			DBObject str = cursor.next();
		}
	}
	/**
	 * 查询
	 * @param query
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> find(Query query, Class<T> entityClass) {
		return mongoTemplate.find(query, entityClass);
	}
	 /**
	  * 查询
	  * @param query
	  * @param entityClass
	  * @param collectionName
	  * @return
	  */
	public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
		return mongoTemplate.find(query, entityClass, collectionName);
	}
	/** 
     * 删除操作
     * @param collectionName 集合名称
     */  
	public void drop(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}
	 /**
	  * 删除
	  * @param query
	  * @param entityClass
	  * @param collectionName
	  */
	public <T> void remove(Query query, Class<T> entityClass, String collectionName) {
		mongoTemplate.remove(query, entityClass, collectionName);
	}
	
}
