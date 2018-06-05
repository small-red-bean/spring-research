package com.redbean.springresearch.quartz;


import org.quartz.Job;

import java.util.Date;

public class JobMetaData {
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 触发器描述
     */
    private String triggerDescription;

    /**
     * 任务表达式
     */
    private String cornExpress;

    /**
     * 任务触发时间
     */
    private Date triggerTime;

    /**
     * true:corn类型任务，false:time类型任务
     */
    private boolean isCorn;

    /**
     * 具体任务
     */
    private Class<? extends Job> jobClazz;

    public JobMetaData(String jobName, String jobGroup, String cornExpress,
                       Date triggerTime, boolean isCorn, Class<? extends Job> jobClazz) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cornExpress = cornExpress;
        this.triggerTime = triggerTime;
        this.isCorn = isCorn;
        this.jobClazz = jobClazz;
    }

    public JobMetaData(String jobName, String jobGroup, String jobDescription, String triggerDescription,
                       String cornExpress, Date triggerTime, boolean isCorn, Class<? extends Job> jobClazz) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobDescription = jobDescription;
        this.triggerDescription = triggerDescription;
        this.cornExpress = cornExpress;
        this.triggerTime = triggerTime;
        this.isCorn = isCorn;
        this.jobClazz = jobClazz;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup == null ? "DEFAULT" : jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCornExpress() {
        return cornExpress;
    }

    public void setCornExpress(String cornExpress) {
        this.cornExpress = cornExpress;
    }

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    public boolean isCorn() {
        return isCorn;
    }

    public void setCorn(boolean corn) {
        isCorn = corn;
    }

    public Class<? extends Job> getJobClazz() {
        return jobClazz;
    }

    public void setJobClazz(Class<? extends Job> jobClazz) {
        this.jobClazz = jobClazz;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTriggerDescription() {
        return triggerDescription;
    }

    public void setTriggerDescription(String triggerDescription) {
        this.triggerDescription = triggerDescription;
    }
}
