package com.DiseaseService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {

    @Value("${spring.application.name:no name}")
    private String AppName;

    @Value("${server.port:no port}")
    private String port;

    @GetMapping("/diseases")
    public String disease(){
        return " List of diseases ";
    }

    @GetMapping("/location")
    public String getDiseaseServiceLocation(){
        return AppName +" : "+ port;
    }
}
