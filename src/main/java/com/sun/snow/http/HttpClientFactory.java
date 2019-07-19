package com.sun.snow.http;

/**
 * http客户端句柄工厂
 * @author TWW
 *
 */
public class HttpClientFactory {

	/**
	 * http客户端句柄
	 */
    private static HttpClientHandler httpClientHandler;
    
    /**
     * 获得http客户端句柄
     * 
     * @return
     */
    public static HttpClientHandler getHttpClientHandler(){
    	httpClientHandler = new HttpClientHandler();
        return httpClientHandler;
    }
	
}
