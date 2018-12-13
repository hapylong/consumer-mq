package com.iqb.consumer.mq.common.util;

import java.util.Random;

/**
 * 生成用户表的userId的公共类
 * @author William
 * 
 */
public class GenerationUtil {
	
	//生成  user 表的 userid 规则 :88(2位)+用户类型(1位)+10位毫秒数+2位随机数   共15 位
	public static String generationUserId(Integer user_type){//userType用户类型
		//获取毫秒数
		Long currTime=System.currentTimeMillis()/1000;
		return "88"+user_type+currTime+getTwoDig();
	}
	
	//生成两位随机数，作为用户号的最后两位,供给generationUserId调用
	public static int getTwoDig(){
		return new Random().nextInt(100);
	}
	
	//生成 account表的acc_no 规则：15位用户号+00（两位默认值）+币种（3位）+账户类型（3位） +2为随机数 == 共25位  作为1级别账户的acct_no 
	public static String generationAccountId(String userId,String currency,String acctType,String acctLevel){
		if("1".equals(acctLevel)){
			return userId+"00"+currency+"999"+getThreeDig();
		}else{
			return userId+"00"+currency+acctType+getThreeDig();
		}
		
	}
	
	//生成三位随机数，
	public static int getThreeDig(){
		return new Random().nextInt(900)+100;
	}
	
	/**
     * 获取20序列号  YYYYMMDDHHMMSS+6位随机数
     * @return
     */
    public static String getSeqNo(){
    	
	     String seqNo = DateTools.getYmdhmsTime();
	     Random random = new Random();
	     for(int i=0;i<6;i++){
	    	 seqNo+=random.nextInt(10);
		 }
	     return seqNo;	
    }
	
}
