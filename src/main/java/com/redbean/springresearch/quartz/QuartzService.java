package com.redbean.springresearch.quartz;


import org.quartz.SchedulerException;

public interface QuartzService {
    /**
     * 创建任务
     * @param jobMetaData
     * @throws SchedulerException
     */
    void create(JobMetaData jobMetaData) throws SchedulerException;

    /**
     * 更新任务
     * @param jobMetaData
     * @throws SchedulerException
     */
    void update(JobMetaData jobMetaData) throws SchedulerException;

    /**
     * 判断job是否已经存在
     * @param jobMetaData
     * @return
     * @throws SchedulerException
     */
    boolean exsit(JobMetaData jobMetaData) throws SchedulerException;

    /**
     * 删除任务
     * @param jobMetaData
     * @throws SchedulerException
     */
    void delete(JobMetaData jobMetaData) throws SchedulerException;
}
