package com.redbean.springresearch.quartz;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;

    private Trigger getTrigger(JobMetaData jobMetaData, TriggerKey triggerKey) {
        Trigger trigger;
        if(jobMetaData.isCorn()) {
            trigger = TriggerBuilder.newTrigger().
                    withIdentity(triggerKey).
                    withSchedule(CronScheduleBuilder.cronSchedule(jobMetaData.getCornExpress())).
                    withDescription(jobMetaData.getTriggerDescription()).build();
        } else {
            trigger = TriggerBuilder.newTrigger().
                    withIdentity(triggerKey).
                    startAt(jobMetaData.getTriggerTime()).
                    withDescription(jobMetaData.getTriggerDescription()).build();
        }
        return trigger;
    }

    @Override
    public void create(JobMetaData jobMetaData) throws SchedulerException {
        JobKey jobKey = new JobKey(jobMetaData.getJobName(), jobMetaData.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(jobMetaData.getJobName(), jobMetaData.getJobGroup());
        JobDetail jobDetail = JobBuilder.newJob(jobMetaData.getJobClazz()).
                withIdentity(jobKey).withDescription(jobMetaData.getJobDescription()).build();
        Trigger trigger = getTrigger(jobMetaData, triggerKey);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void update(JobMetaData jobMetaData) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobMetaData.getJobName(), jobMetaData.getJobGroup());
        Trigger trigger = getTrigger(jobMetaData, triggerKey);
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Override
    public boolean exsit(JobMetaData jobMetaData) throws SchedulerException {
        return scheduler.checkExists(new JobKey(jobMetaData.getJobName(), jobMetaData.getJobGroup()));
    }

    @Override
    public void delete(JobMetaData jobMetaData) throws SchedulerException {
        if(exsit(jobMetaData)) {
            TriggerKey triggerKey = new TriggerKey(jobMetaData.getJobName(), jobMetaData.getJobGroup());
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
        }
    }
}
