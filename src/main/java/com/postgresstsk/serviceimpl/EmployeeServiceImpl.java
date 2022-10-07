package com.postgresstsk.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.postgresstsk.entities.Employee;
import com.postgresstsk.repository.EmployeeRepo;
import com.postgresstsk.response.EmployeeCountResponse;
import com.postgresstsk.response.Resource;
import com.postgresstsk.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo epmloyeeRepo;

	@Override
	public EmployeeCountResponse addEmployee(MultipartFile emp) throws IOException {

		int lineSkiped = 0;
		int processdLine = 0;
		List<Employee> notAdded = new ArrayList<Employee>();

		List<Employee> employees = read(Employee.class, emp.getInputStream());

		Map<Integer, Employee> map = new HashMap<Integer, Employee>();
		for (int i = 0; i < employees.size(); i++)
			map.put(employees.get(i).getEmployee_id(), employees.get(i));

		for (Map.Entry<Integer, Employee> e : map.entrySet()) {
			Employee emplo = e.getValue();
			try {
				int age = Integer.parseInt(emplo.getAge());
				if (emplo.getName().equals("") || emplo.getCountry().equals("") || age == 0) {
					lineSkiped++;
					emplo.setErrorMsg("Name ,country ,age should not be a empty...");
					notAdded.add(emplo);
				} else {
					this.epmloyeeRepo.save(emplo);
					processdLine++;
				}
			} catch (Exception e1) {
				lineSkiped++;
				emplo.setErrorMsg("Age only be a integer...");
				notAdded.add(emplo);
			}
		}

		EmployeeCountResponse result = new EmployeeCountResponse();
		result.setProcessLine(processdLine);
		result.setSkippedLine(lineSkiped);
		result.setNotAdded(notAdded);

		return result;
	}

	private static final CsvMapper mapper = new CsvMapper();

	public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
		CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(clazz).with(schema);
		return reader.<T>readValues(stream).readAll();
	}

	@Override
	public ResponseEntity<Resource> getCsv() {
		
		 String[] csvHeader = {
		            "employee_id", "name","country", "age"
		    };
		
		List<Employee> employees=this.epmloyeeRepo.findAll();
		System.out.println(employees);

		 ByteArrayInputStream byteArrayOutputStream;
		
		try(ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(csvHeader));) 
		{
	        for (Employee record : employees)
	            csvPrinter.printRecord(record);
	        csvPrinter.flush();
	        byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
	    } 
		catch (IOException e) {
	        throw new RuntimeException(e.getMessage());
	    }
		
		InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

	    String csvFileName = "employee.csv";
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
	    headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
	    
	    return Resource.getRsource(null,headers, HttpStatus.OK);
	}

}
