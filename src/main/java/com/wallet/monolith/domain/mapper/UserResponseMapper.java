package com.wallet.monolith.domain.mapper;

import com.wallet.monolith.domain.dto.UserRequest;
import com.wallet.monolith.domain.dto.UserResponse;
import com.wallet.monolith.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserResponseMapper {
    UserResponse userToUserResponse(User user);
    UserResponse userRequestToUserResponse(UserRequest userRequest);

}
