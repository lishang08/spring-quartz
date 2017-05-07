package com.quartz.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.quartz.model.ScheduleJob;
import com.quartz.vo.ScheduleJobVo;

/**
* Author: fulishang
* Create Time  : 2017年5月1日,下午11:46:15
* Modify Time :
* Desc  : 异步执行器
* Blog : https://lishang08.github.io/
*/

public class AsyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger LOGGER = Logger.getLogger(AsyncJobFactory.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("AsyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJobVo.JOB_PARAM_KEY);
        LOGGER.info("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
    }
}
