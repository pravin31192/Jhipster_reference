package com.sun.prms.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class ResourceSearch {
	
	private String empName;
	private String projectName;
    private String clientName;
    //private String projectType;
    
    public String getEmpName() {
  		return empName;
  	}
  	public void setEmpName(String empName) {
  		this.empName = empName;
  	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
   
}
