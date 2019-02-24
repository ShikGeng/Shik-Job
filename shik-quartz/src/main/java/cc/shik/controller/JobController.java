package cc.shik.controller;

import cc.shik.domain.JobEntity;
import cc.shik.service.DynamicJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by EalenXie on 2018/6/4 16:12
 */
@RestController
public class JobController {
    private static Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private DynamicJobService jobService;

    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            reStartAllJobs();
            logger.info("INIT SUCCESS");
        } catch (SchedulerException e) {
            logger.info("INIT EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }

    //根据ID重启某个Job
    @RequestMapping("/refresh/{id}")
    public String refresh(@PathVariable Integer id) throws SchedulerException {
        String result;
        JobEntity entity = jobService.getJobEntityById(id);
        if (entity == null) return "error: id is not exist ";
        TriggerKey triggerKey = new TriggerKey(entity.getName(), entity.getGroup());
        JobKey jobKey = jobService.getJobKey(entity);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getDescription(), map);
            if (entity.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Open Job : " + entity.getName() +  " success !";
            } else {
                result = "Close Job : " + entity.getName() +  " success !";
            }
        } catch (SchedulerException e) {
            result = "Error while Refresh " + e.getMessage();
        }
        return result;
    }

    //重启数据库中所有的Job
    @RequestMapping("/refresh/all")
    public String refreshAll() {
        String result;
        try {
            reStartAllJobs();
            result = "SUCCESS";
        } catch (SchedulerException e) {
            result = "EXCEPTION : " + e.getMessage();
        }
        return "refresh all jobs : " + result;
    }

    /**
     * 重新启动所有的job
     */
    private void reStartAllJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : set) {
            scheduler.deleteJob(jobKey);
        }
        for (JobEntity job : jobService.loadJobs()) {
            logger.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getGroup(), job.getCron());
            JobDataMap map = jobService.getJobDataMap(job);
            JobKey jobKey = jobService.getJobKey(job);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, job.getDescription(), map);
            if (job.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
                logger.info("Job {} open success", job.getName());
            } else {
                logger.info("Job {} close success", job.getName());
            }
        }
    }

    //根据ID关闭某个Job
    @RequestMapping("/close/{id}")
    public String close(@PathVariable Integer id) throws SchedulerException {
        String result;
        jobService.updateJobEntity(id,"CLOSE");
        result = refresh(id);
        return result;
    }

    @RequestMapping("/open/{id}")
    public String open(@PathVariable Integer id) throws SchedulerException {
        String result;
        jobService.updateJobEntity(id,"OPEN");
        result = refresh(id);
        return result;
    }
}
