package com.quartz.model;

import java.util.Date;

import com.dexcoder.commons.pager.Pageable;

/**
* Author: fulishang
* Create Time  : 2017年4月26日,上午12:36:56
* Modify Time :
* Desc  : 定时任务模型
* Blog : https://lishang08.github.io/
*/

public class ScheduleJob extends Pageable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8029227702192700095L;

	//定时任务id
	private Long scheduleJobId;
    //定时任务名称	
	private String jobName;
    //定时任务别名	
	private String aliasName;
	//定时任务组别
	private String jobGroup;
	//定时任务触发器
	private String jobTrigger;
	//定时任务状态
	private String status;
	//定时任务表达式
	private String cronExpression;
	//是否同步
	private Boolean isSync;
	//定时任务描述
	private String description;
	//定时任务创建时间
	private Date gmtCreate;
	//定时任务修改时间
	private Date gmtModify;
	//定时任务url
	private String url;
	public Long getScheduleJobId() {
		return scheduleJobId;
	}
	public void setScheduleJobId(Long scheduleJobId) {
		this.scheduleJobId = scheduleJobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobTrigger() {
		return jobTrigger;
	}
	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Boolean getIsSync() {
		return isSync;
	}
	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "ScheduleJob [scheduleJobId=" + scheduleJobId + ", jobName=" + jobName + ", aliasName=" + aliasName
				+ ", jobGroup=" + jobGroup + ", jobTrigger=" + jobTrigger + ", status=" + status + ", cronExpression="
				+ cronExpression + ", isSync=" + isSync + ", description=" + description + ", gmtCreate=" + gmtCreate
				+ ", gmtModify=" + gmtModify + ", url=" + url + "]";
	}
	
	
}
