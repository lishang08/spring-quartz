package com.quartz.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quartz.service.ScheduleJobService;
import com.quartz.vo.ScheduleJobVo;

/**
 * Author: fulishang 
 * Create Time : 2017年4月26日,上午12:07:48 
 * Modify Time : 
 * Desc : 任务调度控制器 
 * Blog : https://lishang08.github.io/
 */
@Controller
public class ScheduleJobController {

	private static final Logger LOGGER = Logger.getLogger(ScheduleJobController.class);

	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 添加任务
	 * 
	 * @param scheduleJobVo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "input-schedule-job", method = RequestMethod.GET)
	public String inputScheduleJob(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {
		Long jobId = scheduleJobVo.getScheduleJobId();
		if (jobId != null) {
			ScheduleJobVo scheduleJob = scheduleJobService.getScheduleJobById(jobId);
			scheduleJob.setKeywords(scheduleJobVo.getKeywords());
			modelMap.put("scheduleJobVo", scheduleJob);
		}
		return "input-schedule-job";
	}

	/**
	 * 列出当前schedulejob和当前执行schedulejob
	 * @param scheduleJobVo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "list-schedule-job", method = RequestMethod.GET)
	public String listScheduleJob(ScheduleJobVo scheduleJobVo, ModelMap modelMap) {
		List<ScheduleJobVo> scheduleJobVos = scheduleJobService.getScheduleJobList(scheduleJobVo);
		LOGGER.info("总共有 " + (CollectionUtils.isEmpty(scheduleJobVos) ? 0 : scheduleJobVos.size()) + " jobs");
		modelMap.put("scheduleJobVoList", scheduleJobVos);

		List<ScheduleJobVo> executionJobList = scheduleJobService.getExecutingJobList();
		LOGGER.info("总共有 " + (CollectionUtils.isEmpty(executionJobList) ? 0 : executionJobList.size()) + " jobs在执行");
		modelMap.put("executingJobList", executionJobList);
		return "list-schedule-job";
	}

	/**
	 * 保存schedulejob
	 * @param scheduleJobVo
	 * @return
	 */
	@RequestMapping(value = "save-schedule-job", method = RequestMethod.POST)
	public String saveScheduleJob(ScheduleJobVo scheduleJobVo) {
		// 测试用随便设个状态
		scheduleJobVo.setStatus("1");
//		LOGGER.info("Alias name : " + scheduleJobVo.getAliasName());
//		LOGGER.info("Cron Expression : " + scheduleJobVo.getCronExpression());
//		LOGGER.info("JOB ID : " + scheduleJobVo.getScheduleJobId());
//		LOGGER.info("STATUS : " + scheduleJobVo.getStatus());
//		LOGGER.info("DESCRIPTION : " + scheduleJobVo.getDescription());
//		LOGGER.info("JOB NAME : " + scheduleJobVo.getJobName());
//		LOGGER.info("JOB GROUP " + scheduleJobVo.getJobGroup());
		if (scheduleJobVo.getScheduleJobId() == null) {
			scheduleJobService.insert(scheduleJobVo);
		} else if (StringUtils.equalsIgnoreCase(scheduleJobVo.getKeywords(), "delUpdate")) {
			// 直接拿keywords存一下，就不另外重新弄了
			scheduleJobService.delUpdate(scheduleJobVo);
		} else {
			scheduleJobService.update(scheduleJobVo);
		}
		return "redirect:list-schedule-job.shtml";
	}
    
	/**
	 * 暂停schedule job
	 * @param scheduleJobId
	 * @return
	 */
	@RequestMapping(value = "pause-schedule-job", method = RequestMethod.GET)
	public String pauseScheduleJob(Long scheduleJobId) {
		LOGGER.info("暂停job...");
		scheduleJobService.pauseJob(scheduleJobId);
		return "redirect:list-schedule-job.shtml";
	}
	
	/**
	 * 恢复schedule job
	 * @param scheduleJobId
	 * @return
	 */
	@RequestMapping(value = "resume-schedule-job", method = RequestMethod.GET)
	public String resumeScheduleJob(Long scheduleJobId) {
		LOGGER.info("恢复job...");
        scheduleJobService.resumeJob(scheduleJobId);		
		return "redirect:list-schedule-job.shtml";
	}
	
	/**
	 * 运行一次schedule job
	 * @param scheduleJobId
	 * @return
	 */
	@RequestMapping(value = "run-once-schedule-job", method = RequestMethod.GET)
	public String runOnceScheduleJob(Long scheduleJobId) {
		LOGGER.info("运行一次job...");
        scheduleJobService.runOnceJob(scheduleJobId);		
		return "redirect:list-schedule-job.shtml";
	}
	
	@RequestMapping(value = "delete-schedule-job", method = RequestMethod.GET)
	public String deleteScheduleJob(Long scheduleJobId) {
		LOGGER.info("运行一次job...");
        scheduleJobService.delete(scheduleJobId);		
		return "redirect:list-schedule-job.shtml";
	}
	
	
	
}
