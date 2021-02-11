package dev.ershov.vd.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="clientvk")
@Data
public class ClientConfig {
    private String requestUrlTemplate;
    private String token;
    private String confirmationToken;
    private long groupId;
    private float versionAPI;
    private String callbackRequestTemplate;
}
