package com.DoctorPortal;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MainController {

    @Autowired
    EurekaClient eurekaClient;

    RestClient restClient = RestClient.create();

    @GetMapping("/portal-doctors")
    public String getDoctor(){

        InstanceInfo instanceInfo =
                eurekaClient.getNextServerFromEureka("DOCTERSERVICE", false);

        String baseUrl = instanceInfo.getHomePageUrl();

        return restClient.get()
                .uri(baseUrl + "/location")
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/portal-Disease")
    public String getDisease(){

        InstanceInfo instanceInfo =
                eurekaClient.getNextServerFromEureka("DISEASESERVICE", false);

        String baseUrl = instanceInfo.getHomePageUrl();

        return restClient.get()
                .uri(baseUrl + "/location")
                .retrieve()
                .body(String.class);
    }
}
