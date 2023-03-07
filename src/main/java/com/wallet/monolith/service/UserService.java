package com.wallet.monolith.service;

import com.wallet.monolith.domain.dto.UserRequest;
import com.wallet.monolith.domain.dto.UserResponse;
import com.wallet.monolith.domain.model.User;

public interface UserService {

    UserResponse getUserById(Integer id) throws Exception;

    UserResponse loginUser(UserRequest userRequest);

    void saveUser(UserRequest userRequest) throws Exception;

    User getUserByUsername(String username);

}