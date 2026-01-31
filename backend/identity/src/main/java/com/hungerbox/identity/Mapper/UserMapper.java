package com.hungerbox.identity.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hungerbox.identity.dto.request.RegisterOrgRequest;
import com.hungerbox.identity.dto.request.RegisterRequest;
import com.hungerbox.identity.dto.response.UserResponse;
import com.hungerbox.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) 
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) 
	User toEntity(RegisterOrgRequest request);
}