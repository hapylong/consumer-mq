package com.iqb.consumer.mq.asset.allocation.biz;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.iqb.consumer.mq.asset.allocation.base.AssetTransferFFJFAttr;
import com.iqb.consumer.mq.asset.allocation.base.config.AssetAllocationfig;
import com.iqb.consumer.mq.asset.allocation.dao.AssetAllocationDao;
import com.iqb.consumer.mq.asset.allocation.dao.CacheAssetAllocationDao;
import com.iqb.consumer.mq.asset.allocation.dao.MongoDBDao;
import com.iqb.consumer.mq.asset.allocation.push.IAssetPushService;
import com.iqb.consumer.mq.common.base.BaseRedisDao;
import com.iqb.consumer.mq.common.exception.IqbMqException;
import com.iqb.consumer.mq.common.util.DateTools;
import com.iqb.consumer.mq.common.util.JSONUtil;
import com.iqb.consumer.mq.common.util.ReturnCodeType;
import com.iqb.consumer.mq.common.util.StringUtil;
import com.iqb.consumer.mq.common.util.http.HttpsClientUtil;

/**
 * 
 * Description: 资产分配biz
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年10月17日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class AssetAllocationBiz{
    
    private static Logger logger = LoggerFactory.getLogger(AssetAllocationBiz.class);
    
    private static final String RECEIVE = "receive";
    
    private static final String BASEDATA = "basedata";
    
    private static final String BORROWINFO = "borrowInfo";
    
    private static final String ORDER_ID = "order_id";
    
    /** 爱钱帮上标id **/
    private static final String IQB_BORROW_ID = "iqb_borrow_id";
    
    @Autowired
    private CacheAssetAllocationDao cacheAssetAllocationDao;
    
    @Autowired
    private AssetAllocationDao assetAllocationDao;
    
    @Resource(name = "assetAllocationfig")
    private AssetAllocationfig assetAllocationfig;
    
    @Autowired
    private IAssetPushService assetPushServiceImpl;
    
    @Autowired
    private BaseRedisDao baseRedisDao;
    
    @Autowired
    private MongoDBDao mongoDBDao;
    
    /**
     * 
     * Description: 监听资产分配队列信息
     * @param
     * @return Map
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月17日 下午2:47:28
     */
    public Map listenAssetAllocat() throws JsonParseException, JsonMappingException, IOException{
        return cacheAssetAllocationDao.listenAssetAllocat();
    }

    /**
     * 
     * Description: 处理资产分配队列消息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年10月17日 下午2:29:45
     */
    public void dealAssetAllocationMsg(Map beanM) throws Exception{
        String orderId = String.valueOf(beanM.get("projectId"));
    	  synchronized (this) {
              if (orderId == null || StringUtil.isEmpty(orderId)) {
                  throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030001.getRetCode());
              }
              this.pushAssetBean(beanM);
          }
    }
    


    /**
     * Description: 更新资产分配状态
     * 
     * @param info
     */
    public synchronized void updateRequestMoneyByAsset(Map info) {
        this.assetAllocationDao.updateRequestMoneyByAsset(info);
    }
    
    /**
     * 
     * Description: 根据订单id更新资产分配状态
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月6日 下午3:11:40
     */
    public synchronized void updateRequestMoneyByOrderId(Map info) {
        this.assetAllocationDao.updateRequestMoneyByOrderId(info);
    }
    
    private static final String URL_CHANNAL_JUDGE = "channal";
    private static final String CHANNAL_TYPE_RAR  = "CHANNAL_RAR";
    private static final String CHANNAL_JL  = "CHANNAL_JL"; // 金鳞
    
    /**
     * 
     * Description: 推送资产信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月1日 上午10:48:17
     */
	private void pushAssetBean(Map assetPushInfo) throws IqbMqException {
		try {
			logger.info("推送资金开始，资金信息" + JSONUtil.objToJson(assetPushInfo));
			assetPushInfo.put("pushTime", DateTools.getYYYYMMDDMMHHSS());
			mongoDBDao.saveAssetLog(assetPushInfo);
            if(AssetTransferFFJFAttr.PUSH_ASSET_TO_IQB.equals(assetPushInfo.get(RECEIVE))){
                /** 推送爱钱帮  **/
                this.pushToIQB(assetPushInfo);
            }else{
                /** 推送饭饭金服  **/
                this.pushToFFJF(assetPushInfo);
            }
			mongoDBDao.saveAssetLog(assetPushInfo);
		} catch (IqbMqException e) {
			logger.error("推送资金至饭饭异常", e);
			assetPushInfo.put("error", e);
			mongoDBDao.saveAssetLog(assetPushInfo);
			if(!"asset_01030005".equals(e.getRetCodeType().getRetCode())){
			    logger.info("推送资金至饭饭异常，将推送信息重新推入队列，项目信息：{}", JSONUtil.objToJson(assetPushInfo));
			    baseRedisDao.leftPush("queue_asset_allocation_list",  JSONUtil.objToJson(assetPushInfo));
			}
			throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030003.getRetCode());
		} catch (Exception e1){
			logger.error("推送资金至饭饭异常", e1);
			assetPushInfo.put("error", e1);
			mongoDBDao.saveAssetLog(assetPushInfo);
			logger.info("推送资金至饭饭异常，将推送信息重新推入队列，项目信息：{}", JSONUtil.objToJson(assetPushInfo));
			baseRedisDao.leftPush("queue_asset_allocation_list",  JSONUtil.objToJson(assetPushInfo));
			throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030003.getRetCode());
		}
    }
	
	/**
	 * 
	 * Description: 推送到爱钱帮
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2017年4月6日 下午2:55:04
	 */
    private void pushToIQB(Map assetPushInfo) throws IqbMqException {
        String retStr = HttpsClientUtil.getInstance().doPost(this.assetAllocationfig.getUrlOfIQBPush(), JSONObject.toJSONString(assetPushInfo), "utf-8");
        logger.info("爱钱帮返回信息：" + retStr);
        assetPushInfo.put("response", retStr);
        String orderIdReq = this.getOrderIdFromReq(assetPushInfo);
        if (StringUtil.isEmpty(retStr)) {
            this.assetAllocationDao.deleteReqMoney(orderIdReq);
            throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030003.getRetCode());
        }
        String orderId = null;
        try {
            orderId = getIQBReponseResult(retStr);
        } catch (Exception e) {
            this.assetAllocationDao.deleteReqMoney(orderIdReq);
            e.printStackTrace();
            logger.info(e.getMessage());
            logger.error(e.getMessage());
        }
        if (orderId != null) {
            JSONObject strToJSON = JSONUtil.strToJSON(retStr);
            assetPushInfo.put("orderId", orderId);
            assetPushInfo.put("createtime", DateTools.getYYYYMMDDMMHHSS());
            assetPushInfo.put("sourcesFunding", AssetTransferFFJFAttr.PUSH_ASSET_TO_IQB);
            assetPushInfo.put("responseId", strToJSON.getInteger(IQB_BORROW_ID));
            this.updateRequestMoneyByOrderId(assetPushInfo);
        }
    }

    /**
     * 
     * Description: 从请求信息中取出orderId
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月12日 下午2:39:18
     */
    private String getOrderIdFromReq(Map assetPushInfo) {
        String orderId = assetPushInfo.get(BASEDATA) == null ? null : ((Map)assetPushInfo.get(BASEDATA)).get(BORROWINFO) == null ? null : ObjectUtils.toString(((Map)((Map)assetPushInfo.get(BASEDATA)).get(BORROWINFO)).get(ORDER_ID));
        return orderId;
    }

    /**
     * 
     * Description: 从爱钱帮返回结果中解析orderId
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月6日 下午3:12:06
     */
    private String getIQBReponseResult(String retStr) throws IqbMqException {
        JSONObject strToJSON = JSONUtil.strToJSON(retStr);
        String success = strToJSON.getString("success");
        if ("1".equals(success)) {
            String orderId = strToJSON.getString("orderId");
            if(StringUtil.isEmpty(orderId)){
                throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030009.getRetCode());
            }
            return orderId;
        } else if ("2".equals(success)) {
            throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030008.getRetCode());
        }
        return null;
    }

    /**
	 * 
	 * Description: 推送至饭饭金服
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2017年4月1日 上午10:48:28
	 */
    private void pushToFFJF(Map assetPushInfo) throws IqbMqException {
        String channal = ObjectUtils.toString(assetPushInfo.get(URL_CHANNAL_JUDGE));
        String retStr = null;
        if (StringUtil.isEmpty(channal)) {
            retStr = this.assetPushServiceImpl.crmPushByPost(assetAllocationfig.getUrlOfFFJFPush(), assetPushInfo);
        } else if (channal.equals(CHANNAL_TYPE_RAR)) {
            retStr = this.assetPushServiceImpl.crmPushByPost(assetAllocationfig.getUrlOfRARPush(), assetPushInfo);
        } else if (channal.equals(CHANNAL_JL)) {
            retStr = this.assetPushServiceImpl.crmPushByPost(assetAllocationfig.getUrlOfJL(), assetPushInfo);
        }
        logger.info("资金返回信息：" + retStr);
        assetPushInfo.put("response", retStr);
        if (StringUtil.isEmpty(retStr)) {
        	throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030003.getRetCode());
        }
        String responseId = getReponseResult(retStr);
        if (responseId != null) {
        	assetPushInfo.put("responseId", responseId);
        	assetPushInfo.put("createtime", DateTools.getYYYYMMDDMMHHSS());
        	this.updateRequestMoneyByAsset(assetPushInfo);
        }
    }

    /**
     * 
     * Description: 处理饭饭返回结果
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年4月1日 上午10:49:28
     */
    private String getReponseResult(String result) throws IqbMqException {
        JSONObject strToJSON = JSONUtil.strToJSON(result);
        String success = (String) strToJSON.get("success");
        if ("1".equals(success)) {
            JSONObject iqbResult = (JSONObject) strToJSON.get("iqbresult");
            if (iqbResult != null) {
                return  iqbResult.getString("result");
            }
        } else if ("2".equals(success)) {
            JSONObject iqbResult = (JSONObject) strToJSON.get("iqbresult");
            if (iqbResult != null) {
                if ("000001".equals((String) iqbResult.get("retCode"))) {
                    throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030005.getRetCode());
                } else {
                    throw new IqbMqException(ReturnCodeType.ASSET_PUSH_01030006.getRetCode());
                }
            }
        }
        return null;
    }
}
