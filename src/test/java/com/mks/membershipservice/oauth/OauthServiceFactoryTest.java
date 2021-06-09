package com.mks.membershipservice.oauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class OauthServiceFactoryTest {
    @Autowired
    private OauthServiceFactory oauthServiceFactory;
    @Autowired
    private KakaoOauthService kakaoOauthService;
    @Autowired
    private NaverOauthService naverOauthService;

    @Test
    @DisplayName("oauth 타입에 따라 해당되는 oauth service 가져오기")
    void oauth_타입별로_해당되는_서비스_가져오기() {
        OauthService oauthService = oauthServiceFactory.getOauthService(OauthType.KAKAO);
        assertThat(oauthService).isSameAs(kakaoOauthService);

        oauthService = oauthServiceFactory.getOauthService(OauthType.NAVER);
        assertThat(oauthService).isSameAs(naverOauthService);

        for(OauthType oauthType : OauthType.values()){
            oauthService = oauthServiceFactory.getOauthService(oauthType);
            assertThat(oauthService.getOauthType()).isEqualTo(oauthType);
        }
    }
}
