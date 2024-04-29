package study.openfeign.presentation.controllerservice;

public interface ControllerService {

    String getRedirectURL();

    String authorize(String code);
}
