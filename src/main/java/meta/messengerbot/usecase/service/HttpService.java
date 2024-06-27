package meta.messengerbot.usecase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {

    private static final Logger logger = LoggerFactory.getLogger(HttpService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${facebook.page.access.token}")
    private String accessToken;

    public void sendMessage(Map<String, Object> messageContent) {
        String url = "https://graph.facebook.com/v12.0/me/messages?access_token=" + accessToken;
        logger.info("Sending message: {}", messageContent);
        restTemplate.postForObject(url, messageContent, String.class);
    }
}