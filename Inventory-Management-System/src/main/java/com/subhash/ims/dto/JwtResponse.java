package com.subhash.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    /**
     * JWT Access Token
     */
    private String accessToken;

    /**
     * Token Type
     * Default: Bearer
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Token Expiration (milliseconds)
     */
    private Long expiresIn;

    /**
     * Logged-in User Information
     */
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String role;

    private LocalDateTime loginTime;

}
