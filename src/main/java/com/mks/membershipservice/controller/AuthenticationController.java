package com.mks.membershipservice.controller;

import com.mks.membershipservice.service.AuthenticationService;
import com.mks.membershipservice.vo.RequestLogin;
import com.mks.membershipservice.vo.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody RequestLogin requestLogin) {
        ResponseLogin responseLogin = authenticationService.login(requestLogin.getUsername(), requestLogin.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
    }
}
