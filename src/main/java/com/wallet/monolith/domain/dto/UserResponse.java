package com.wallet.monolith.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer userId;
    private String username;
    private String fullName;
    private String jwt;
}
