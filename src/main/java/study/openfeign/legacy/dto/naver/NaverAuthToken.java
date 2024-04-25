package study.openfeign.legacy.dto.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverAuthToken(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("refresh_token") String refreshToken
) {
}
