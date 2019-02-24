package cc.shik.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project shik-elastic-job
 * @package cc.shik.job
 * @since 2019-01-14
 */
@Component
public class SimpleDemoJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(SimpleDemoJob.class);

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                logger.info("Job item 0 ...");
                break;
            case 1:
                logger.info("Job item 1 ...");
                break;
            case 2:
                logger.info("Job item 2 ...");
                break;
            // case n: ...
        }
    }
}
