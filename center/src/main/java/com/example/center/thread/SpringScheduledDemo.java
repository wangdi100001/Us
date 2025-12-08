package com.example.center.thread;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling // 开启调度
public class SpringScheduledDemo {

    // 固定延迟：上一次任务结束后5秒执行
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        System.out.println("Spring固定延迟任务执行");
    }

    // 固定频率：上一次任务开始后3秒执行（需注意任务执行时间是否超过频率）
    @Scheduled(fixedRate = 3000)
    public void fixedRateTask() {
        System.out.println("Spring固定频率任务执行");
    }

    // Cron表达式：每分钟的第10秒执行（Cron格式：秒 分 时 日 月 周 年（可选））
    @Scheduled(cron = "10 * * * * ?")
    public void cronTask() {
        System.out.println("Spring Cron任务执行");
    }

}
