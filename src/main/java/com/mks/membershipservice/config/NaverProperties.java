package com.mks.membershipservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@ConfigurationProperties(prefix = "oauth.naver")
public class NaverProperties {

    @NotEmpty
    private String restApiKey;

    @NotEmpty
    private String redirectUrl;

    @NotEmpty
    private String authUrl;

    @NotEmpty
    private String password;

    @NotEmpty
    private String clientSecret;
}
