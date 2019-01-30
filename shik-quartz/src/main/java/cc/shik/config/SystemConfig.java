package cc.shik.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author Shik
 * @project shik-job
 * @package cc.shik.config
 * @since 2019-01-30
 */
@Data
@Configuration
public class SystemConfig {

    @Value("${server.port}")
    private int port;
}
