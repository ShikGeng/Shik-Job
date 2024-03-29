package cc.shik;

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
@SpringBootApplication
public class ShikElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShikElasticJobApplication.class);
    }


}
