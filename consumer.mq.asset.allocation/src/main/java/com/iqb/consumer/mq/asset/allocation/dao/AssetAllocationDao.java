package com.iqb.consumer.mq.asset.allocation.dao;

import java.util.Map;

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
public interface AssetAllocationDao{
    
    /**
     * 
     * Description: 测试db
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月17日 下午3:41:07
     */
    public Map<String, Object> testDb();


    /**
     * Description 更新资产推送状态
     * 
     * @param info
     */
    public void updateRequestMoneyByAsset(Map info);
    
    /**
     * 
     * Description: 根据订单id更新资产推送状态
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月6日 下午3:11:04
     */
    public void updateRequestMoneyByOrderId(Map info);

    /**
     * 
     * Description: 删除资产分配信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月12日 下午2:45:41
     */
    public void deleteReqMoney(String orderIdReq);

}
