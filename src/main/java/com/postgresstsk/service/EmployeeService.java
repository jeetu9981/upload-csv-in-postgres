package com.postgresstsk.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.postgresstsk.response.EmployeeCountResponse;
import com.postgresstsk.response.Resource;

public interface EmployeeService {
	public EmployeeCountResponse addEmployee(MultipartFile emp) throws IOException;
	public ResponseEntity<Resource> getCsv();
}
