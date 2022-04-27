package com.alkemy.ong.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponseDto(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public JwtAuthResponseDto(String accessToken, String tokenType) {
        super();
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
