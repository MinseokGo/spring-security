package study.openfeign.legacy.dto.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import study.openfeign.legacy.dto.AuthToken;

public record GoogleAuthToken(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        String scope) implements AuthToken {
}
