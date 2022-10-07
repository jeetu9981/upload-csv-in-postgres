package com.postgresstsk.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.postgresstsk.response.EmployeeCountResponse;
import com.postgresstsk.response.Resource;
import com.postgresstsk.response.Response;
import com.postgresstsk.serviceimpl.EmployeeServiceImpl;


@MultipartConfig
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	Logger logger=LoggerFactory.getLogger(EmployeeController.class);
	
	@PostMapping("/addemployee")
	public ResponseEntity<Response> addEmployee(@RequestParam("csv") MultipartFile csvFile) throws IOException{
		EmployeeCountResponse emp=this.employeeServiceImpl.addEmployee(csvFile);
		if(emp!=null) {
			if(emp.getNotAdded().size()>0) {
				for(int i=0;i<emp.getNotAdded().size();i++)
					logger.error(emp.getNotAdded().get(i)+"  "+emp.getNotAdded().get(i).getErrorMsg());
			}
			return Response.getResponse("Employee Added...",HttpStatus.CREATED,emp);
		}
		else
			return Response.getResponse("Employee Not Added...",HttpStatus.INTERNAL_SERVER_ERROR, null);
	}
	
	@GetMapping("/getcsv")
	public ResponseEntity<Resource> getCsv(){
		return this.employeeServiceImpl.getCsv();
	}
}
