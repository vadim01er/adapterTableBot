package dev.ershov.vd.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TgClient {

    private final RestTemplate template;
    private final TgClientConfig tgClientConfig;

    public TgClient(RestTemplate template, TgClientConfig tgClientConfig) {
        this.template = template;
        this.tgClientConfig = tgClientConfig;
    }

    private String getUrlRequest(String methodName, Map<String, Object> props) {
        String properties = props.entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining("&"));
        String requestURL =
                String.format(tgClientConfig.getRequestUrlTemplate(), tgClientConfig.getToken(), methodName);
        if (!props.isEmpty()) {
            requestURL += "?" + properties;
        }
        return requestURL;
    }

    public ResponseEntity<MessageResponse> sendMessage(String message, int chatId) {
        final String url = getUrlRequest(
                "sendMessage",
                new HashMap<>() {{
                    put("text", message);
                    put("chat_id", chatId);
                }}
        );
        log.info("trying GET request: " + url);
        final ResponseEntity<MessageResponse> response = template.getForEntity(url, MessageResponse.class);
        return response;
    }
}
