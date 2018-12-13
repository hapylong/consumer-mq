package com.iqb.consumer.mq.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class StringUtil extends StringUtils{

	/**
	 * 判断字符串是否匹配regexs，只要匹配任何一个，则返回true
	 * @param source 源字符串
	 * @param regexs 正则表达式数组
	 * @return
	 */
	public static boolean matches(String source, String... regexs){
		for(String regex : regexs){
			if(source.matches(regex)){
				return true;
			}
		}
		return false;
	}
	
	
	
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	
	
    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
    	if(htmlStr == null){
    		return null;
    	}
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr.trim(); // 返回文本字符串
    }
    
    /**
     * 判断字符串是否为空字符串，如果str为"null"，认为其不为空字符串
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return (str == null || str.isEmpty());
    }
}
