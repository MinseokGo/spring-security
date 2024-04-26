package study.openfeign.legacy.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/{name}")
public class FrontController {

    private final ControllerServiceCondition condition;

    @GetMapping
    public String redirect(@PathVariable("name") String name) {
        ControllerService controllerService = condition.getControllerService(name);
        return Objects.requireNonNull(controllerService).create();
    }

    @ResponseBody
    @GetMapping("/redirect")
    public String callback(@PathVariable("name") String name,
                           @RequestParam("code") String code) {

        ControllerService controllerService = condition.getControllerService(name);
        Objects.requireNonNull(controllerService).auth(code);
        return name;
    }
}
