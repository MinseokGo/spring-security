package study.openfeign.legacy.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakao")
public class AuthProperties {

    private String clientId;
    private String redirectUri;
    private String clientSecret;
}
