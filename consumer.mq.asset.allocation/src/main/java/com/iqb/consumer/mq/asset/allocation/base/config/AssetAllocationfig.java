package com.iqb.consumer.mq.asset.allocation.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Description: 资产配置配置
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年9月29日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Configuration
public class AssetAllocationfig{

    @Value("${url_of_ffjf_push}")
    private String urlOfFFJFPush;
    
    @Value("${url_of_iqb_push}")
    private String urlOfIQBPush;

    @Value("${url_of_rar_push}")
    private String urlOfRARPush;
    
    @Value("${url_of_jl}")
    private String urlOfJL;

    public String getUrlOfJL() {
        return urlOfJL;
    }

    public void setUrlOfJL(String urlOfJL) {
        this.urlOfJL = urlOfJL;
    }

    public String getUrlOfFFJFPush() {
        return urlOfFFJFPush;
    }

    public void setUrlOfFFJFPush(String urlOfFFJFPush) {
        this.urlOfFFJFPush = urlOfFFJFPush;
    }

    public String getUrlOfIQBPush() {
        return urlOfIQBPush;
    }

    public void setUrlOfIQBPush(String urlOfIQBPush) {
        this.urlOfIQBPush = urlOfIQBPush;
    }

    public String getUrlOfRARPush() {
        return urlOfRARPush;
    }

    public void setUrlOfRARPush(String urlOfRARPush) {
        this.urlOfRARPush = urlOfRARPush;
    }
}
