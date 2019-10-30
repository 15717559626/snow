package com.sun.snow.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientConfig {
	

	private static CloseableHttpClient httpclient;
	private static PoolingHttpClientConnectionManager connManager;
	
	private static IdleConnectionMonitorThread monitorThread;

	static {
		connManager = new PoolingHttpClientConnectionManager();

		try {
			// 设置连接池大小
			connManager.setMaxTotal(1000);
			connManager.setDefaultMaxPerRoute(1000);

			ConnectionConfig connectionConfig = ConnectionConfig
					.custom()
					.setCharset(Consts.UTF_8)
					.build();

			connManager.setDefaultConnectionConfig(connectionConfig);

			httpclient = HttpClientBuilder
					.create()
					.setUserAgent("Apache-HttpClient4.5.2 / CZBTOC")
					.setConnectionManager(connManager)
					.build();

//			monitorThread = new IdleConnectionMonitorThread(connManager);
//			monitorThread.start();
//
//			Runtime runtime = Runtime.getRuntime();
//			runtime.addShutdownHook(new CloseThread());

			log.info("HTTP CLIENT初始化成功");

		} catch (NumberFormatException e) {
			log.error("HTTP CLIENT初始化失败，请检查配置文件数字值是否正确");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error("HTTP CLIENT初始化失败："+e.getMessage());
			log.error(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient build(){
		return httpclient;
	}
	
	/**
	 * 关闭HttpClient线程
	 * @author 780330
	 * @version 2017年2月4日 上午8:53:09
	 *
	 */
	static class CloseThread extends Thread{

		@Override
		public void run() {
			try {
				monitorThread.shutdown();
				connManager.shutdown();
				httpclient.close();
			} catch (IOException e) {
				log.error("HTTP CLIENT关闭失败："+e.getMessage());
				log.error(e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 * 空闲线程监控
	 * @author 780330
	 * @version 2017年2月4日 上午8:52:48
	 *
	 */
	public static class IdleConnectionMonitorThread extends Thread {
	    
	    private final HttpClientConnectionManager connMgr;
	    private volatile boolean shutdown;
	    
	    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
	        super();
	        this.connMgr = connMgr;
	    }

	    @Override
	    public void run() {
	        try {
	            while (!shutdown) {
	                synchronized (this) {
	                    wait(5000);
	                    // Close expired connections
	                    connMgr.closeExpiredConnections();
	                    // Optionally, close connections
	                    // that have been idle longer than 30 sec
	                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
	                    
	                    //PoolStats ps = connManager.getTotalStats();
	                    //logger.info(ps.toString());
	                }
	            }
	        } catch (InterruptedException ex) {
				log.error("HTTP CLIENT空闲线程监控报错："+ex.getMessage());
				log.error(ex.getMessage(), ex);
				Thread.currentThread().interrupt();
	        }
	    }
	    
	    public void shutdown() {
	        shutdown = true;
	        synchronized (this) {
	            notifyAll();
	        }
	    }
	    
	}
}

