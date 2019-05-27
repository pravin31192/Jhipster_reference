package com.sun.prms.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class ResourceCountOnProjectGraphDTO {
   private String projectName;
   private Integer resourceCount;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(Integer resourceCount) {
		this.resourceCount = resourceCount;
	}
	   
	   
	}
