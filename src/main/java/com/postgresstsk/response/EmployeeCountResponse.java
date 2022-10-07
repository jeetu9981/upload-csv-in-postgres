package com.postgresstsk.response;

import java.util.List;

import com.postgresstsk.entities.Employee;

public class EmployeeCountResponse {
	private int skippedLine;
	private int processLine;
	
	private List<Employee> notAdded;
	
	
	
	public List<Employee> getNotAdded() {
		return notAdded;
	}
	public void setNotAdded(List<Employee> notAdded) {
		this.notAdded = notAdded;
	}
	public int getSkippedLine() {
		return skippedLine;
	}
	public void setSkippedLine(int skippedLine) {
		this.skippedLine = skippedLine;
	}
	public int getProcessLine() {
		return processLine;
	}
	public void setProcessLine(int processLine) {
		this.processLine = processLine;
	}
	
	
}
