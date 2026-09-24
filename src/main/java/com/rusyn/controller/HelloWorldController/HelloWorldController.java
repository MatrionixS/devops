package com.rusyn.controller.HelloWorldController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/helloWorld")
public class HelloWorldController {

    @GetMapping
    public String helloWorld(){
        return "HelloWorld";
    }
}
