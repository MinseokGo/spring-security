package study.openfeign.legacy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DefaultService {

    private final RestTemplate restTemplate;

    public HttpHeaders setHttpHeaders(String... values) {
        HttpHeaders headers = new HttpHeaders();
        for (int i = 0; i < values.length; i += 2) {
            headers.add(values[i], values[i + 1]);
        }
        return headers;
    }

    public MultiValueMap<String, String> createRequestBody(String... values) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        for (int i = 0; i < values.length; i += 2) {
            body.add(values[i], values[i + 1]);
        }
        return body;
    }

    public <T> T restTemplate(String requestURL,
                               HttpMethod httpMethod,
                               HttpEntity<MultiValueMap<String, String>> request,
                               Class<T> mappingClass) {

        return restTemplate.exchange(requestURL, httpMethod, request, mappingClass)
                .getBody();
    }
}
