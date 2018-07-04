package com.redbean.springresearch.controller;


import com.alibaba.fastjson.JSON;
import com.redbean.springresearch.encrypt.RSACoder;
import com.redbean.springresearch.http.Data;
import com.redbean.springresearch.job.SelfDemoJob;
import com.redbean.springresearch.quartz.JobMetaData;
import com.redbean.springresearch.quartz.QuartzService;
import com.redbean.springresearch.util.CommunicationUtil;
import com.redbean.springresearch.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

@RestController
public class DemoController {
    @Autowired
    private QuartzService quartzService;

    @RequestMapping("/demo")
    public void demo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Data data = CommunicationUtil.readJson(request, Data.class);
            data.setId(46445646);
            CommunicationUtil.responseClient(response, JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }


//        JobMetaData jobMetaData = new JobMetaData(
//                "Demo",
//                null,
//                "0 */10 * * * ?",
//                null,
//                true,
//                SelfDemoJob.class);
//        try {
//            if(quartzService.exsit(jobMetaData)) {
//                quartzService.update(jobMetaData);
//            } else {
//                quartzService.create(jobMetaData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }




}
