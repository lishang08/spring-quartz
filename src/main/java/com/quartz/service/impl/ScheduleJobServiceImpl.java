package com.quartz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.build.Criteria;
import com.quartz.model.ScheduleJob;
import com.quartz.service.ScheduleJobService;
import com.quartz.utils.ScheduleUtils;
import com.quartz.vo.ScheduleJobVo;

/**
 * Author: fulishang Create Time : 2017年4月26日,上午1:02:38 Modify Time : Desc :
 * 实现定时任务服务 Blog : https://lishang08.github.io/
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	private static Logger LOGGER = Logger.getLogger(ScheduleJobServiceImpl.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private JdbcDao jdbcDao;

	public void initScheduleJob() {
		// TODO Auto-generated method stub
		LOGGER.info("Start to initialize schedule job...");
		List<ScheduleJob> scheduleJobList = jdbcDao.queryList(Criteria.select(ScheduleJob.class));
		if (scheduleJobList.isEmpty()) {
			return;
		} else {
			// 遍历ScheduleJob
			for (ScheduleJob scheduleJob : scheduleJobList) {
				CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
						scheduleJob.getJobGroup());
				// 如果cronjob不存在则创建
				if (cronTrigger == null) {
					ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
				} else {
					// 如果存在则更新
					ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
				}
			}
		}
	}

	/**
	 * 创建新的schedulejob
	 */
	public Long insert(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		return jdbcDao.insert(scheduleJob);
	}

	/**
	 * 更新schedulejob
	 */
	public void update(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		jdbcDao.update(scheduleJob);
	}

	/**
	 * 删除schedulejob
	 */
	public void delete(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		if (scheduleJob == null) {
			LOGGER.info("数据库中找不到相应的schedule job！！！");
		} else {
			// 删除运行任务
			ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
			// 将任务从数据库中删除
			jdbcDao.delete(scheduleJob);
		}
	}

	/**
	 * 根据id查找schedulejob
	 */
	public ScheduleJobVo getScheduleJobById(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		return scheduleJob.getTargetObject(ScheduleJobVo.class);
	}

	/**
	 * 返回schedule job 列表
	 */
	public List<ScheduleJobVo> getScheduleJobList(ScheduleJobVo scheduleJobVo) {

		List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class,
				jdbcDao.queryList(scheduleJobVo.getTargetObject(ScheduleJob.class)));
		try {
			for (ScheduleJobVo vo : scheduleJobVoList) {
				JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
				List<? extends Trigger> triggers =  scheduler.getTriggersOfJob(jobKey);
				if (CollectionUtils.isEmpty(triggers)) {
					continue;
				}
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scheduleJobVoList;
	}

    /**
     * 获取运行中的job列表
     * @return
     */
	@Override
    public List<ScheduleJobVo> getExecutingJobList() {
        try {
            // 存放结果集
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();

            // 获取scheduler中的JobGroupName
            for (String group:scheduler.getJobGroupNames()){
                // 获取JobKey 循环遍历JobKey
            	LOGGER.info("GROUP : " + group);
                for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
                    ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
                    BeanConverter.convert(scheduleJobVo,scheduleJob);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJobVo.setJobTrigger(trigger.getKey().getName());
                    scheduleJobVo.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJobVo.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if(triggerState.name().equals("NORMAL")){
                        jobList.add(scheduleJobVo);
                    }
                }
            }
            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }

	@Override
    public void delUpdate(ScheduleJobVo scheduleJobVo) {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        jdbcDao.update(scheduleJob);
    }

	@Override
	public void pauseJob(Long  scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}

	@Override
	public void resumeJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}

	@Override
	public void runOnceJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}
}
