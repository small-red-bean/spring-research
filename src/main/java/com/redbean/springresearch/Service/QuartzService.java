package com.redbean.springresearch.Service;

import com.redbean.springresearch.job.SelfDemoJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuartzService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private DemoService demoService;


    public void test() {
        System.out.println("=============start===============");

        String cronExpress = "0 */1 * * * ?";
        String instanceName = "autoEnterMatch";
        String jobKeyName = instanceName + "_JOBDETAIL";
        String triggerKeyName = instanceName + "_TRIGGER";
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("DemoService", demoService);

        try {
            create(jobKeyName, triggerKeyName, dataMap, SelfDemoJob.class, instanceName, cronExpress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=============end===============");
    }


    private synchronized void create(String jobKeyName, String triggerKeyName, JobDataMap dataMap, Class<? extends Job> clazz, String instanceName, String cornExpress) throws Exception {
        JobDetail job = JobBuilder
                .newJob(clazz)
                .withIdentity(jobKeyName)
                .build();
        JobDataMap jobDataMap = job.getJobDataMap();
        jobDataMap.putAll(dataMap);
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerKeyName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cornExpress))
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
