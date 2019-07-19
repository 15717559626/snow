package com.sun.snow.http;

import java.io.UnsupportedEncodingException;


public class ResponseEntity {
	
	private String contentType;
	
	private String encoding="UTF-8";
	
	private int statusCode;
	
	private byte[] bytesResult;
	
	private String strResult;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public byte[] getBytesResult() {
		return bytesResult;
	}

	public void setBytesResult(byte[] bytesResult) {
		this.bytesResult = bytesResult;
	}

	public String getStrResult() throws UnsupportedEncodingException {
		if(null == this.strResult){
			if(null != this.encoding)
				this.strResult = new String(this.bytesResult,this.encoding);
			else
				this.strResult = new String(this.bytesResult);
		}
		return this.strResult;
	}

	public void setStrResult(String strResult) {
		this.strResult = strResult;
	}
	
}
