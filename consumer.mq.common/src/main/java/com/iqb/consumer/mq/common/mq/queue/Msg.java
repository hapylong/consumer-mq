package com.iqb.consumer.mq.common.mq.queue;

import java.io.Serializable;

/**
 * 
 * @author jack
 *
 */
public class Msg implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public String id;
	
	public String type;
	
	public Object obj;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public String callBackUrlPublic;
	public String callBackUrlManager;

	public String getCallBackUrlPublic() {
		return callBackUrlPublic;
	}
	public void setCallBackUrlPublic(String callBackUrlPublic) {
		this.callBackUrlPublic = callBackUrlPublic;
	}
	public String getCallBackUrlManager() {
		return callBackUrlManager;
	}
	public void setCallBackUrlManager(String callBackUrlManager) {
		this.callBackUrlManager = callBackUrlManager;
	}
}