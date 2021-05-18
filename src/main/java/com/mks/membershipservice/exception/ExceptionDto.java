package com.mks.membershipservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExceptionDto {
    private HttpStatus error;
    private String error_description;

    public ExceptionDto(HttpStatus error, String error_description){
        this.error = error;
        this.error_description = error_description;
    }
}
