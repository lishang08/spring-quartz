package com.quartz.service;

import java.awt.color.ICC_ColorSpace;
import java.util.List;

import com.quartz.vo.ScheduleJobVo;

/**
* Author: fulishang
* Create Time  : 2017年4月26日,上午12:48:55
* Modify Time :
* Desc  : 任务服务
* Blog : https://lishang08.github.io/
*/

public interface ScheduleJobService {

	/**
	 * 初始化定时任务
	 */
	public void initScheduleJob();
	
	/**
	 * 新增定时任务
	 * @param scheduleJobVo
	 * @return
	 */
	public Long insert(ScheduleJobVo scheduleJobVo);
	
	/**
	 * 更新定时任务
	 * @param scheduleJobVo
	 */
	public void update(ScheduleJobVo scheduleJobVo);
	
	/**
	 * 根据scheduleJobId删除定时任务
	 * @param scheduleJobId
	 */
	public void delete(Long scheduleJobId);
	
	/**
	 * 根据scheduleJobId获取任务对象
	 * @param scheduleJobId
	 * @return
	 */
	public ScheduleJobVo getScheduleJobById(Long scheduleJobId);
	
	/**
	 * 查询任务列表
	 * @param scheduleJobVo
	 * @return
	 */
	public List<ScheduleJobVo> getScheduleJobList(ScheduleJobVo scheduleJobVo);
}
