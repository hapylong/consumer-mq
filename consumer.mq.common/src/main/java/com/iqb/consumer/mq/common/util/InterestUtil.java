package com.iqb.consumer.mq.common.util;

import java.math.BigDecimal;

/**
 * 计息工具类
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年2月16日-下午3:43:56
 */
public class InterestUtil {
	
	/**
	 * 通用计息方法    计算日收益 = 本金 * 预期日化收益率
	 * 预期日化收益率(x * 365 = 预期年化收益率)
	 * @param capital 本金
	 * @param yearRate 预期年化收益率
	 * @return
	 */
	public static BigDecimal commonInterest(BigDecimal capital,BigDecimal yearRate){		
		BigDecimal dateRate = yearRate.divide(new BigDecimal(365),8,BigDecimal.ROUND_DOWN);
		BigDecimal dayIncome = capital.multiply(dateRate);
		return dayIncome.setScale(2,BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 反推计息方法    计算日收益 = 本金 * 预期日化收益率
	 * 预期日化收益率 ((x+1)的365次方 = 1 + 预期年化收益)
	 * @param capital 本金
	 * @param yearRate 预期年化收益率
	 * @return
	 */
	public static BigDecimal specialInterest(BigDecimal capital,BigDecimal yearRate){
		
		BigDecimal paraFactor = new BigDecimal(1);
		double para1Rate = yearRate.add(paraFactor).doubleValue();
		
		//(1+x)的365次方 -1 = 8.88%
		double para2Rate = Math.pow(para1Rate, 1.0/365);		
		BigDecimal dateRate = new BigDecimal(para2Rate).subtract(paraFactor);		
		dateRate = dateRate.setScale(5,BigDecimal.ROUND_DOWN);
		BigDecimal dayIncome = capital.multiply(dateRate);
		return dayIncome.setScale(2,BigDecimal.ROUND_DOWN);
	}
}