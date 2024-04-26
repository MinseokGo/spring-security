package study.openfeign.legacy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.openfeign.legacy.controller.controllerservice.ControllerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/{name}")
public class FrontController {

    private final ControllerServiceCondition condition;

    @GetMapping
    public String redirect(@PathVariable("name") String name) {
        ControllerService controllerService = condition.getControllerService(name);
        return controllerService.getRedirectURL();
    }

    @ResponseBody
    @GetMapping("/redirect")
    public String callback(@PathVariable("name") String name,
                           @RequestParam("code") String code) {

        ControllerService controllerService = condition.getControllerService(name);
        controllerService.oauth(code);
        return name;
    }
}
