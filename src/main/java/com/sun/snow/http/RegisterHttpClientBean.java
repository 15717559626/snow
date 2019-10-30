package com.sun.snow.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * httpclient 实例配置
 *
 * @author : yhy ✌️
 * @date : 2018/5/5 10:02
 */
@Configuration
public class RegisterHttpClientBean {

    /**
     * 长连接保持时间，单位为s
     */
    @Value("${httpclient.config.keepAliveTime}")
    private int keepAliveTime = 30;
    /**
     * 连接超时或异常重试次数
     */
    @Value("${httpclient.config.retryTime}")
    private int retryTime;

    /**
     * 连接池最大连接数
     */
    @Value("${httpclient.config.connMaxTotal}")
    private int connMaxTotal = 20;

    /**
     *
     */
    @Value("${httpclient.config.maxPerRoute}")
    private int maxPerRoute = 20;

    /**
     * 连接存活时间，单位为s
     */
    @Value("${httpclient.config.timeToLive}")
    private int timeToLive = 60;
    /**
     * 连接超时时间，单位ms
     */
    @Value("${httpclient.config.connectTimeout}")
    private int connectTimeout = 5000;

    /**
     * 请求超时时间
     */
    @Value("${httpclient.config.connectRequestTimeout}")
    private int connectRequestTimeout = 5000;

    /**
     * sock超时时间
     */
    @Value("${httpclient.config.socketTimeout}")
    private int socketTimeout = 2000;

    @Bean(name = "connectionKeepAliveStrategy")
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return getConnectionKeepAliveStrategy();
    }

    private static ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        return (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            return 30 * 1000;
        };
    }


    @Bean(name = "poolHttpcConnManager")
    public PoolingHttpClientConnectionManager poolingClientConnectionManager() {
        PoolingHttpClientConnectionManager poolHttpcConnManager = new PoolingHttpClientConnectionManager(60, TimeUnit.SECONDS);
        // 最大连接数
        poolHttpcConnManager.setMaxTotal(this.connMaxTotal);
        // 路由基数
        poolHttpcConnManager.setDefaultMaxPerRoute(this.maxPerRoute);
        return poolHttpcConnManager;
    }

    @Bean(name = "config")
    public RequestConfig config() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(this.connectRequestTimeout)
                .setConnectTimeout(this.connectTimeout)
                .setSocketTimeout(this.socketTimeout)
                .build();
    }

    /**
     * 描述：HttpClient的重试处理机制
     */
    @Bean
    public HttpRequestRetryHandler httpRequestRetryHandler() {
        // 请求重试
        final int retryTime = this.retryTime;
        return getHttpRequestRetryHandler(retryTime);
    }

    private static HttpRequestRetryHandler getHttpRequestRetryHandler(int retryTime) {
        return (exception, executionCount, context) -> {
            // Do not retry if over max retry count,如果重试次数超过了retryTime,则不再重试请求
            if (executionCount >= retryTime) {
                return false;
            }
            // 服务端断掉客户端的连接异常
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            // time out 超时重试
            if (exception instanceof InterruptedIOException) {
                return true;
            }
            // Unknown host
            if (exception instanceof UnknownHostException) {
                return false;
            }
            // Connection refused
            // SSL handshake exception
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            return !(request instanceof HttpEntityEnclosingRequest);
        };
    }
}
