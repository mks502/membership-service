package com.mks.membershipservice.controller;

import com.mks.membershipservice.exception.BadRequestException;
import com.mks.membershipservice.service.AuthenticationService;
import com.mks.membershipservice.vo.RequestLogin;
import com.mks.membershipservice.vo.RequestLogout;
import com.mks.membershipservice.vo.ResponseLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody RequestLogin requestLogin) {
        ResponseLogin responseLogin = authenticationService.login(requestLogin.getUsername(), requestLogin.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
    }
    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody RequestLogout requestLogout){
        authenticationService.logout(requestLogout.getToken());
        return ResponseEntity.status(HttpStatus.OK).body("logout");
    }
}
