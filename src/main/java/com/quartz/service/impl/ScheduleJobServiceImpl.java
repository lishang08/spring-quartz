package com.quartz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.quartz.service.ScheduleJobService;
import com.quartz.vo.ScheduleJobVo;

/**
* Author: fulishang
* Create Time  : 2017年4月26日,上午1:02:38
* Modify Time :
* Desc  : 实现定时任务服务
* Blog : https://lishang08.github.io/
*/
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService{

	private static Logger LOGGER = Logger.getLogger(ScheduleJobServiceImpl.class);
	
	public void initScheduleJob() {
		// TODO Auto-generated method stub
		LOGGER.info("Start to initialize schedule job...");
	}

	public Long insert(ScheduleJobVo scheduleJobVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(ScheduleJobVo scheduleJobVo) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long scheduleJobId) {
		// TODO Auto-generated method stub
		
	}

	public ScheduleJobVo getScheduleJobById(Long scheduleJobId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ScheduleJobVo> getScheduleJobList(ScheduleJobVo scheduleJobVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
