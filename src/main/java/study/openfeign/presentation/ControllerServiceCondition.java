package study.openfeign.presentation;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.presentation.controllerservice.ControllerService;

@Component
@RequiredArgsConstructor
public class ControllerServiceCondition {

    private final Map<String, ControllerService> controllerServiceMap;

    public ControllerService getControllerService(String name) {
        return controllerServiceMap.get(name);
    }
}
