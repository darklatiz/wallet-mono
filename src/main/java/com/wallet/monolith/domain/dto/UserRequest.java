package com.wallet.monolith.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Integer userId;
    private String userName;
    private String password;
    private String fullName;
    private String jwt;
}
