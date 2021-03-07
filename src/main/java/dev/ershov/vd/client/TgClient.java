package dev.ershov.vd.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
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

    private String getUrlRequest(String methodName) {

        return String.format(tgClientConfig.getRequestUrlTemplate(), tgClientConfig.getToken(), methodName);
    }

    public ResponseEntity<MessageResponse> sendMessage(String message, int chatId) {
        final String url = getUrlRequest("sendMessage");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("chat_id", String.valueOf(chatId));
        map.add("text", message);
        log.info("trying POST request: " + url + " chat_id = " + chatId + "text = " + message.substring(0, 30));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        final ResponseEntity<MessageResponse> response = template.postForEntity(url, request, MessageResponse.class);
        return response;
    }

    public String sendPhoto(InputStream file, String name,  int chatId) {
        final String url = getUrlRequest(
                "sendPhoto",
                new HashMap<>() {{
                    put("chat_id", chatId);
                }}
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("photo", new MultipartInputStreamFileResource(file, name));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        log.info("trying POST request: " + url);
        final String response = template.postForObject(url, requestEntity, String.class);
        return response;
    }

    public class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }
}
