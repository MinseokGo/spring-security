package study.openfeign.legacy.properties;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google")
public class GoogleAuthProperties implements AuthProperties {

    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String requestUri;
    private String scope;

    @Override
    public String mapping() {
        Map<String, String> propertiesMap = getPropertiesMap();
        StringBuilder result = new StringBuilder("redirect:" + requestUri);
        propertiesMap.keySet()
                .forEach(key -> result.append("&").append(key).append("=").append(propertiesMap.get(key)));
        return result.toString();
    }

    private Map<String, String> getPropertiesMap() {
        return Map.of(
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "scope", scope);
    }
}
