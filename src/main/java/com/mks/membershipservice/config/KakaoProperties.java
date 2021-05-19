package com.mks.membershipservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties {

    @NotEmpty
    private String restApiKey;

    @NotEmpty
    private String redirectUrl;

    @NotEmpty
    private String kakaoAuthUrl;

    @NotEmpty
    private String password;
}
