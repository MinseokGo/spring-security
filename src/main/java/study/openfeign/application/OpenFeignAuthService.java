package study.openfeign.application;

public interface OpenFeignAuthService {

    String authorization(String code);
}
