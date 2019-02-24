package cc.shik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project shik-job
 * @package cc.shik
 * @since 2019-01-30
 */
@EnableScheduling
@SpringBootApplication
public class ShikScheduleApplication {

    private static final Logger logger = LoggerFactory.getLogger(ShikScheduleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShikScheduleApplication.class);
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void testA() throws InterruptedException {
        Thread.sleep(2500);
        logger.info(" A schedule job test .....");
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void testB() {
        logger.info(" B schedule job test .....");
    }
}
