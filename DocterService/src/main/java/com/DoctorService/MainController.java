package com.DoctorService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Value("${spring.application.name:no name}")
    private String AppName;

    @Value("${server.port:no port}")
    private String port;

    @GetMapping("/doctors")
    public String doctors(){
        return " List of doctors ";
    }

    @GetMapping("/location")
    public String geetDoctorLocation(){
        return AppName +" : "+ port;
    }
}
