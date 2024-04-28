package study.openfeign.legacy.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.controller.controllerservice.ControllerService;

@Component
@RequiredArgsConstructor
public class ControllerServiceCondition {

    private final Map<String, ControllerService> controllerServiceMap;

    public ControllerService getControllerService(String name) {
        return controllerServiceMap.get(name);
    }
}
