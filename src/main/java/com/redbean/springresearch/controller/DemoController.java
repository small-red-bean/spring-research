package com.redbean.springresearch.controller;


import com.redbean.springresearch.job.SelfDemoJob;
import com.redbean.springresearch.quartz.JobMetaData;
import com.redbean.springresearch.quartz.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private QuartzService quartzService;

    @RequestMapping("/demo")
    public void demo() {
        JobMetaData jobMetaData = new JobMetaData(
                "Demo",
                null,
                "0 */10 * * * ?",
                null,
                true,
                SelfDemoJob.class);
        try {
            if(quartzService.exsit(jobMetaData)) {
                quartzService.update(jobMetaData);
            } else {
                quartzService.create(jobMetaData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
