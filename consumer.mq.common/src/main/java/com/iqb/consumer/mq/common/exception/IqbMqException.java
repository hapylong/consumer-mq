package com.iqb.consumer.mq.common.exception;

import com.iqb.consumer.mq.common.util.ReturnCodeType;


/**
 * 投资异常类处理
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年1月21日-下午4:25:56
 */
public class IqbMqException  extends Exception{
	
	
	/**default sid **/
	private static final long serialVersionUID = -448984715745533135L;
	
	/**返回代码枚举类型**/
	private ReturnCodeType retCodeType ;
	
	
	public ReturnCodeType getRetCodeType() {
		return retCodeType;
	}

	public void setRetCodeType(ReturnCodeType retCodeType) {
		this.retCodeType = retCodeType;
	}

	public IqbMqException(String retCode){
		super(new StringBuffer("调用投资接口发生异常@@ 错误信息:").append(ReturnCodeType.getReturnCodeTypeByCode(retCode)).toString());
		retCodeType =ReturnCodeType.getReturnCodeTypeByCode(retCode);
	}
}