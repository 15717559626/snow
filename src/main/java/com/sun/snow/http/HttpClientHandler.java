package com.sun.snow.http;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ContentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpClientHandler {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientHandler.class);

	@Autowired
	private HttpClientManager httpClientManager;
	
	/**
	 * get 请求
	 * @param url
	 * @return
	 */
	public ResponseEntity doGet(String url){
		RequestEntity reqEntity = new RequestEntity();
		reqEntity.setType(REQ_TYPE.GET);
		reqEntity.setUri(url);
		ResponseEntity resEntity = null;
		try
		{
			resEntity = httpClientManager.exec(reqEntity);
		} catch (Exception e)
		{
			log.error(log.getName()+" =>httpclient执行get请求发生异常：",e);
		}
		return resEntity;
	}
	
	/**
	 * 多格式表单提交，如文件上传
	 * @param url
	 * 			提交URL
	 * @param nvPairs
	 * 			提交文本表单键值对
	 * @param bodies
	 * 			提交二进制数据
	 * @return
	 */
	public ResponseEntity postMultipartEntity(String url, List<NameValuePair> nvPairs, List<ContentBody> bodies){
		RequestEntity reqEntity = new RequestEntity();
		reqEntity.setType(REQ_TYPE.POST);
		reqEntity.setUri(url);
		reqEntity.setContentType(ContentType.create("multipart/form-data", "UTF-8"));
		reqEntity.setNvPairs(nvPairs);
		reqEntity.setBodies(bodies);
		ResponseEntity resEntity = null;
		try
		{
			resEntity = httpClientManager.execMultipart(reqEntity);
		} catch (Exception e)
		{
			log.error(log.getName()+" =>httpclient执行postFormEntity请求发生异常：",e);
		}
		return resEntity;
	}
	
	/**
	 * post JSON数据请求
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public ResponseEntity postJsonEntity(String url, String jsonStr){
		return this.postEntity(url, "application/json", jsonStr);
	}
	
	/**
	 * post XML数据请求
	 * @param url
	 * @param xmlStr
	 * @return
	 */
	public ResponseEntity postXMLEntity(String url, String xmlStr){
		return this.postEntity(url, "application/xml", xmlStr);
	}
	
	public ResponseEntity postEntity(String url, String contentType, String queryStr){
		return postEntity(url, contentType, null,queryStr);
	}
	
	public ResponseEntity postEntity(String url, String contentType, List<NameValuePair> nvPairs){
		return postEntity(url, contentType, nvPairs, null);
	}
	
	/**
	 * post请求
	 * @param url
	 * @param contentType
	 * @param queryStr
	 * @return
	 */
	public ResponseEntity postEntity(String url, String contentType, List<NameValuePair> nvPairs, String queryStr){
		RequestEntity reqEntity = new RequestEntity();
		reqEntity.setType(REQ_TYPE.POST);
		reqEntity.setUri(url);
		reqEntity.setContentType(ContentType.create(contentType,"UTF-8"));
		reqEntity.setNvPairs(nvPairs);
		reqEntity.setQueryStr(queryStr);
		ResponseEntity resEntity = null;
		try
		{
			resEntity = httpClientManager.exec(reqEntity);
		} catch (Exception e)
		{
			log.error(log.getName()+" =>httpclient执行postEntity请求发生异常：",e);
		}
		return resEntity;
	}

}
