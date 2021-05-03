package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@CrossOrigin("*")
public class StaticWebPageController {

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thymeleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        model.put("mul", "2 * 2 = ");
        model.put("plus", "2 * 2  + 2 = ");
        model.put("min", "2 - 2 * 2 = ");

        return "index";
    }
}
