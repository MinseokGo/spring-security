package study.openfeign.legacy.controller.controllerservice;

public interface ControllerService {

    String getRedirectURL();

    void oauth(String code);
}
