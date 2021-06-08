package com.mks.membershipservice.oauth;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OauthServiceFactory {
    private final Map<OauthType, OauthService> oauthServices = new HashMap<>();

    public OauthServiceFactory(List<OauthService> oauthServices) {
        if(CollectionUtils.isEmpty(oauthServices)){
            throw new IllegalArgumentException("존재하는 oauthService 가 없습니다");
        }

        for(OauthService oauthService: oauthServices){
            this.oauthServices.put(oauthService.getOauthType(), oauthService);
        }
    }

    public OauthService getOauthService(OauthType oauthType) {
        return oauthServices.get(oauthType);
    }
}
