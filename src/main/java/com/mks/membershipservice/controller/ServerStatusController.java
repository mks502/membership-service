package com.mks.membershipservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatusController {
    @GetMapping("/health")
    public String healthCheck(){
        return "Membership service health ok";
    }
}
