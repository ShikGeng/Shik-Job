package cc.shik;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest implements Runnable {

    private String jobName = "";

    public ScheduledExecutorTest(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("Time is " + LocalDateTime.now() + ", Thread name is " + Thread.currentThread().getName() + ", execute " + jobName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        long delay1 = 1;
        long period1 = 1;
        // 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
        service.scheduleAtFixedRate(new ScheduledExecutorTest("job1"), delay1, period1, TimeUnit.SECONDS);
        long delay2 = 2;
        long period2 = 2;
        // 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2
        service.scheduleWithFixedDelay(new ScheduledExecutorTest("job2"), delay2, period2, TimeUnit.SECONDS);
    }
}
