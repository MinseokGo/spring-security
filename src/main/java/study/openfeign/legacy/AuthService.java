package study.openfeign.legacy;

public interface AuthService {

    void create(String code);

    void getAccessToken(String code);

    void getUserProfile(String accessToken);
}
