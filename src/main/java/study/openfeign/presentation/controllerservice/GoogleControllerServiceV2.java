package study.openfeign.presentation.controllerservice;

import static study.openfeign.legacy.utils.Constants.GOOGLE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.application.GoogleOpenFeignAuthService;
import study.openfeign.legacy.properties.GoogleAuthProperties;
import study.openfeign.legacy.utils.URLUtils;

@Component(GOOGLE)
@RequiredArgsConstructor
public class GoogleControllerServiceV2 implements ControllerService {

    private final GoogleAuthProperties googleAuthProperties;
    private final GoogleOpenFeignAuthService googleOpenFeignAuthService;

    @Override
    public String getRedirectURL() {
        return URLUtils.createURL(googleAuthProperties);
    }

    @Override
    public String authorize(String code) {
        return googleOpenFeignAuthService.authorization(code);
    }
}
