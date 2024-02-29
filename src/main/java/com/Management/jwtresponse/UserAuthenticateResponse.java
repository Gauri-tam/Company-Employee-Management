package com.Management.jwtresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateResponse {

    @JsonProperty("Access Token")
    private String accessToken;

    @JsonProperty("Refresh Token") //Refresh Token : --------------
    private String refreshToken;
}
