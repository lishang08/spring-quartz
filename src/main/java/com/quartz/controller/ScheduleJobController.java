package com.quartz.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Author: fulishang
* Create Time  : 2017年4月26日,上午12:07:48
* Modify Time :
* Desc  : 任务调度控制器
* Blog : https://lishang08.github.io/
*/

public class ScheduleJobController {

	/**
	 * 任务列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "list-schedule-job", method = RequestMethod.GET)
	public String listScheduleJob(ModelMap modelMap) {
		return "list-schedule-job";
	}
	
}
