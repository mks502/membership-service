package com.mks.membershipservice.oauth;

import com.mks.membershipservice.vo.ResponseLogin;

public interface OauthService {
    ResponseLogin getOrCreateMember(String authorizationCode) throws Exception;

    String getOauthAccessCode(String authorizationCode) throws Exception;

    OauthType getOauthType();
}
