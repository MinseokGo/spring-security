package study.openfeign.legacy.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.controller.controllerservice.ControllerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ControllerServiceCondition {

    private final Map<String, ControllerService> controllerServiceMap;

    public ControllerService getControllerService(String name) {
        controllerServiceMap.keySet().stream().forEach(System.out::println);
        return controllerServiceMap.get(name);
    }
}
