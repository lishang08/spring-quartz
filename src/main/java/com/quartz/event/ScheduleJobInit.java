package com.quartz.event;

import javax.annotation.PostConstruct;

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
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {
        scheduleJobService.initScheduleJob();
    }
	
}
