package com.henry.online_shopping.mapper;

import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.dto.response.UserResponse;
import com.henry.online_shopping.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserResponse requestToDto(RegisterRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isCredentialsExpired", ignore = true)
    @Mapping(target = "isAccountLocked", ignore = true)
    @Mapping(target = "isAccountExpired", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "canAuthenticate", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    User responseToModel(UserResponse response);
}
