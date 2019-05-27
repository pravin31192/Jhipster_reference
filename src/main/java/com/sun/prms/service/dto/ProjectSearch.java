package com.sun.prms.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class ProjectSearch {
	
	private String projectName;
    private String clientName;
    private String projectStatus;
    private String projectType;
    private String percentageComplete;
    
	
	/*public Float getPercentageComplete() {
		return percentageComplete;
	}
	public void setPercentageComplete(Float percentageComplete) {
		this.percentageComplete = percentageComplete;
	}*/
    
    
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	public String getPercentageComplete() {
		return percentageComplete;
	}

	public void setPercentageComplete(String percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
}
