package com.PatientService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @Value("${spring.application.name:no name}")
    private String AppName;

    @Value("${server.port:no port}")
    private String port;

    @GetMapping("/patients")
    public String patients(){
        return " List of patients ";
    }

    @GetMapping("/location")
    public String getPatientsServiceLocation(){
        return AppName +" : "+ port;
    }
}
