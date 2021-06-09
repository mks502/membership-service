package com.mks.membershipservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class NaverController {
    @RequestMapping("/login/naver")
    public String naverLogin() {
        log.info("Naver 로그인 창 접속");
        return "Naver";
    }

}
