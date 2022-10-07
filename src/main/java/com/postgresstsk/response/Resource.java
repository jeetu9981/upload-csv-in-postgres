package com.postgresstsk.response;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Resource {
	private InputStreamResource fileInputStream;
	private HttpHeaders headers;
	private HttpStatus status;
	
	public Resource(InputStreamResource fs,HttpHeaders h,HttpStatus s) {
		this.fileInputStream=fs;
		this.headers=h;
		this.status=s;
	}
	
	public static ResponseEntity<Resource> getRsource(InputStreamResource fs,HttpHeaders h,HttpStatus s){
		Resource r=new Resource(fs, h, s);
		ResponseEntity<Resource> entity=new ResponseEntity<Resource>(r,s);
		return entity;
	}

	public InputStreamResource getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStreamResource fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	
}
