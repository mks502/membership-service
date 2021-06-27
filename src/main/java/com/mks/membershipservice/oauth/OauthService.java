package com.mks.membershipservice.oauth;

import com.mks.membershipservice.dto.OauthDto;
import com.mks.membershipservice.dto.ResponseLogin;

public interface OauthService {
    ResponseLogin getOrCreateMember(OauthDto oauthDto) throws Exception;

    String getOauthAccessCode(String authorizationCode) throws Exception;

    OauthType getOauthType();
}
