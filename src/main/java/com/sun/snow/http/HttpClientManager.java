package com.sun.snow.http;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class HttpClientManager {

	private static final Logger log = LoggerFactory.getLogger(HttpClientManager.class);

	// 从connManager连接池获取连接等待超时时间，毫秒100
	private Integer connectionRequestTimeout = 100;
	// 建立请求连接超时时间，2000毫秒
	private Integer connectTimeout = 10000;
	// 等待数据响应返回超时时间,3000毫秒
	private Integer socketTimeout = 10000;

	private CloseableHttpClient client;
	private RequestConfig requestConfig;
	private static PoolingHttpClientConnectionManager connManager = null;
	
	// 连接池最大连接数
	private static Integer maxTotal = 200;
	// 每个主机路由最大连接数
	private static Integer defaultMaxPerRoute = 200;
	
	static
	{
		try
		{
			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
				}
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
				}
			} }, null);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
			connManager.setDefaultSocketConfig(socketConfig);
			// 消息约束，设置最大header数量，设置每行最大长度
			MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
			ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(maxTotal);
			connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
			log.info(log.getName()+" =>httpClientManager init success!");
		} catch (KeyManagementException e)
		{
			log.error(log.getName()+" =>httpClientManager init faied...",e);
		} catch (NoSuchAlgorithmException e)
		{
			log.error(log.getName()+" =>httpClientManager init faied...",e);
		}
	}

	public HttpClientManager() {
		super();
		// client = HttpClientBuilder.create().build();//不使用连接池
		this.client = HttpClients.custom().setConnectionManager(connManager).build();
		this.requestConfig = RequestConfig.custom().setConnectionRequestTimeout(this.connectionRequestTimeout).setConnectTimeout(this.connectTimeout).setSocketTimeout(this.socketTimeout).build();
	}

	/**
	 * 普通表单提交，执行http请求
	 * @param reqEntity
	 * 			reqEntity请求对象
	 * @return
	 * 			resEntity响应对象
	 * @throws IOException 
	 */
	public ResponseEntity exec(RequestEntity reqEntity) throws HttpException, IOException{
		ResponseEntity resEntity = null;
		HttpRequestBase request = null;
		HttpEntity httpEntity = null;
		CloseableHttpResponse clHttpResponse = null;
		try
		{
			if(reqEntity.getType()==REQ_TYPE.GET){
				request = new HttpGet(reqEntity.getUri());
			} else if(reqEntity.getType()==REQ_TYPE.POST){
				HttpPost httpPost = new HttpPost(reqEntity.getUri());
				if(null != reqEntity.getQueryStr() && !"".equals(reqEntity.getQueryStr()))
					httpPost.setEntity(new StringEntity(reqEntity.getQueryStr(), reqEntity.getContentType()));
				if(null != reqEntity.getNvPairs() && reqEntity.getNvPairs().size()>0)
					httpPost.setEntity(new UrlEncodedFormEntity(reqEntity.getNvPairs(),reqEntity.getContentType().getCharset()));
					
				request = httpPost;
			} else if(reqEntity.getType()==REQ_TYPE.DELETE){
				request = new HttpDelete(reqEntity.getUri());
			} else if(reqEntity.getType()==REQ_TYPE.PUT){
				HttpPut httpPut = new HttpPut(reqEntity.getUri());
				if(null != reqEntity.getQueryStr() && !"".equals(reqEntity.getQueryStr()))
					httpPut.setEntity(new StringEntity(reqEntity.getQueryStr(), reqEntity.getContentType()));
				if(null != reqEntity.getNvPairs() && reqEntity.getNvPairs().size()>0)
					httpPut.setEntity(new UrlEncodedFormEntity(reqEntity.getNvPairs(),reqEntity.getContentType().getCharset()));
				
				request = httpPut;
			}
			// request添加Content-Type请求头header
			if(null != reqEntity.getContentType())
				request.addHeader(HttpHeaders.CONTENT_TYPE,reqEntity.getContentType().toString());
			// 设置requestConfig
			request.setConfig(requestConfig);
			request.addHeader(HttpHeaders.USER_AGENT,
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");
			clHttpResponse = client.execute(request);
			if(null != clHttpResponse){
				int statusCode = clHttpResponse.getStatusLine().getStatusCode();
            	resEntity = new ResponseEntity();
				httpEntity = clHttpResponse.getEntity();
				this.setResponseEntity(httpEntity, resEntity);
				resEntity.setStatusCode(statusCode);
			}
		} finally{
			close(httpEntity,request,clHttpResponse);
		}
		return resEntity;
	}
	
	/**
	 * 多格式表单提交，如文件上传
	 * @param reqEntity
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public ResponseEntity execMultipart(RequestEntity reqEntity) throws HttpException, IOException {
        HttpRequestBase request = null;
        CloseableHttpResponse clHttpResponse = null;
        HttpEntity httpEntity = null;
        ResponseEntity resEntity = null;
        try {
            HttpPost httpPost = new HttpPost(reqEntity.getUri());
            
            //对请求的表单域进行填充 
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if(null != reqEntity.getNvPairs()){
	            for (NameValuePair nameValuePair : reqEntity.getNvPairs()) {
	                entityBuilder.addPart(nameValuePair.getName(),
	                        new StringBody(nameValuePair.getValue(), ContentType.create("text/plain", "UTF-8")));
	            }
            }
            if(null != reqEntity.getBodies()){
	            for (ContentBody contentBody : reqEntity.getBodies()) {
	                entityBuilder.addPart("file", contentBody);
	            }
            }
            
            entityBuilder.setCharset(CharsetUtils.get(reqEntity.getContentType().getCharset().toString()));
            httpPost.setEntity(entityBuilder.build());
            request = httpPost;
            if(null != reqEntity.getContentType())
            	request.addHeader(HttpHeaders.CONTENT_TYPE,reqEntity.getContentType().toString());
            
            clHttpResponse = client.execute(request);
 
            if(null != clHttpResponse){
            	int statusCode = clHttpResponse.getStatusLine().getStatusCode();
            	resEntity = new ResponseEntity();
				httpEntity = clHttpResponse.getEntity();
				this.setResponseEntity(httpEntity, resEntity);
				resEntity.setStatusCode(statusCode);
			}
        } finally {
        	close(httpEntity,request,clHttpResponse);
        }
        return resEntity;
    }
	
	private void close(HttpEntity entity, HttpRequestBase request, CloseableHttpResponse response) throws IOException {
        if (request != null)
            request.releaseConnection();
        if (entity != null)
            entity.getContent().close();
        if (response != null)
            response.close();
    }
	
	/**
	 * 
	 * @param entity
	 * @param resEntity
	 * @throws IOException
	 */
	private void setResponseEntity(HttpEntity entity,ResponseEntity resEntity) throws IOException{
		resEntity.setEncoding(this.getContentEncoding(entity));
		resEntity.setContentType(this.getContentType(entity));
		resEntity.setBytesResult(EntityUtils.toByteArray(entity));
	}
	
	/**
	 * 获取响应contentType
	 * @param entity
	 * @return
	 */
	private String getContentType(HttpEntity entity){
		Header contentType = entity.getContentType();
		if(contentType == null)
			return "text/plain";
		return contentType.getValue();
	}

	/**
	 * 获取响应contentEncoding
	 * @param entity
	 * @return
	 */
	private String getContentEncoding(HttpEntity entity){
		Header contentEncoding = entity.getContentEncoding();
		String encoding = null;
		if(null != contentEncoding){
			encoding = contentEncoding.getValue();
		} else {
			Header contentType = entity.getContentType();
			if(null != contentType){
				String contentTypeStr = contentType.getValue();
				int charsetPos = contentTypeStr.indexOf("charset=");
				if(charsetPos >=0){
					encoding = contentTypeStr.substring(charsetPos+8);
				} else {
					int encodingPos = contentTypeStr.indexOf("encoding=");
					if(encodingPos >= 0){
						encoding = contentTypeStr.substring(encodingPos+9);
					}
				}
			}
		}
		return encoding;
	}
	
}
