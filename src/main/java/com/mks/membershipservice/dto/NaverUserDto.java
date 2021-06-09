package com.mks.membershipservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
//네이버에서 응답해주는 형식에 맞춰 사용자 정보를 가져옴
public class NaverUserDto {
	@JsonProperty("resultcode")
	private String resultCode;
	@JsonProperty("response")
    private Map<String, String> userInfo;

    public String getUserName() {
        return this.userInfo.get("nickname");
    }

    public String getProfileImage() {
        return this.userInfo.get("profile_image");
    }

    public String getId(){
        return this.userInfo.get("id");
    }
}