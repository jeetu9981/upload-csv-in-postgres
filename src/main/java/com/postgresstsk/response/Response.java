package com.postgresstsk.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
	private String msg;
	private HttpStatus status;
	private Object obj;
	
	public Response(String msg,HttpStatus status,Object obj) {
		this.msg=msg;
		this.status=status;
		this.obj=obj;
	}
	
	public static ResponseEntity<Response> getResponse(String msg,HttpStatus status,Object obj) {
		Response res=new Response(msg, status, obj);
		ResponseEntity<Response> responseEntity =new ResponseEntity<Response>(res,status);
		return responseEntity;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
