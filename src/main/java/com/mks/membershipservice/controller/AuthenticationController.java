package com.mks.membershipservice.controller;

import com.mks.membershipservice.service.AuthenticationService;
import com.mks.membershipservice.vo.RequestLogin;
import com.mks.membershipservice.vo.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestLogin requestLogin) {
        ResponseLogin responseLogin = authenticationService.login(requestLogin.getUsername(), requestLogin.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
    }
}
