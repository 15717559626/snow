package com.sun.snow.http;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ContentBody;

import java.util.List;

public class RequestEntity {

	// 请求类型：GET/POST
	private REQ_TYPE type;
	// 请求资源
	private String uri;
	// 请求名称
	private String req_name;
	// 请求contentType，包括mimeType & charset 默认为
	private ContentType contentType = ContentType.create("text/plain", "UTF-8");
	// 请求参数串
	private String queryStr;
	// 请求参数键值对
	private List<NameValuePair> nvPairs;
	// 请求附件
	private List<ContentBody> bodies;

	public REQ_TYPE getType() {
		return type;
	}

	public void setType(REQ_TYPE type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getReq_name() {
		return req_name;
	}

	public void setReq_name(String req_name) {
		this.req_name = req_name;
	}


	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public List<NameValuePair> getNvPairs() {
		return nvPairs;
	}

	public void setNvPairs(List<NameValuePair> nvPairs) {
		this.nvPairs = nvPairs;
	}

	public List<ContentBody> getBodies() {
		return bodies;
	}

	public void setBodies(List<ContentBody> bodies) {
		this.bodies = bodies;
	}
	
	
}
