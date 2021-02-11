package dev.ershov.vd.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="clienttg")
@Data
public class TgClientConfig {
    private String requestUrlTemplate;
    private String token;
}
