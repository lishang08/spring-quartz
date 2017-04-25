package com.quartz.event;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quartz.service.ScheduleJobService;

/**
* Author: fulishang
* Create Time  : 2017年4月26日,上午12:46:49
* Modify Time :
* Desc  : 初始化定时任务
* Blog : https://lishang08.github.io/
*/

@SuppressWarnings("restriction")
@Component
public class ScheduleJobInit {

	private static final Logger LOGGER = Logger.getLogger(ScheduleJobInit.class);
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {

        if (LOGGER.isInfoEnabled()) {
        	LOGGER.info("init");
        }

        scheduleJobService.initScheduleJob();

        if (LOGGER.isInfoEnabled()) {
        	LOGGER.info("end");
        }
    }
	
}
