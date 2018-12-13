package com.iqb.consumer.mq.asset.allocation.dao;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iqb.consumer.mq.common.base.BaseRedisDao;
import com.iqb.consumer.mq.common.util.JSONUtil;

/**
 * 
 * Description: 资产分配dao
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
@Repository("cacheAssetAllocationDao")
public class CacheAssetAllocationDao extends BaseRedisDao {
    
    /**
     * 注入redis服务
     */
    @Autowired
    private BaseRedisDao baseRedisDao;
    
    public Map listenAssetAllocat() throws JsonParseException, JsonMappingException, IOException {
        String jsonStr = baseRedisDao.rightPop(super.BIZ_REDIS_QUEUE_ASSET_ALLOCATION_KEY);
        if(null == jsonStr){
            return null;
        }
        return JSONUtil.toMap(jsonStr);
    }

}
