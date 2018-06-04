package com.redbean.springresearch.job;


import com.redbean.springresearch.Service.DemoService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SelfDemoJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        DemoService demoService = (DemoService)jobDataMap.get("DemoService");
        demoService.sayHello();
    }
}
