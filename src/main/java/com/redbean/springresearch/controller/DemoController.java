package com.redbean.springresearch.controller;


import com.redbean.springresearch.Service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private QuartzService quartzService;

    @RequestMapping("/demo")
    public void demo() {
        quartzService.test();
    }
}
