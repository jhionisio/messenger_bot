package meta.messenger_bot.usecase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Service
public class HttpService {

    @Value("${facebook.page.access.token}")
    private String accessToken;

    private static final Logger logger = LoggerFactory.getLogger(HttpService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(Map<String, Object> messageContent) {
        try {
            logger.info("Sending message: {}", messageContent);

            // Configurar a URL dinamicamente com o accessToken
            String url = "https://graph.facebook.com/v12.0/me/messages?access_token=" + accessToken;

            // Create HttpHeaders
            HttpHeaders headers = new HttpHeaders();

            // Create HttpEntity with headers and body
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(messageContent, headers);

            // Send the request
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Message sent successfully");
            } else {
                logger.error("Failed to send message, status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error sending message", e);
        }
    }
}
