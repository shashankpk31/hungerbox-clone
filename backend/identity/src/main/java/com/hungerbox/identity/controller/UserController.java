package com.hungerbox.identity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.identity.Mapper.UserMapper;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.identity.dto.response.UserResponse;
import com.hungerbox.identity.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	UserRepository userRepository;
	UserMapper userMapper;

	public UserController(UserRepository userRepository, UserMapper userMapper) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}

	@GetMapping(path = "/{id}")
	UserResponse getUser(@PathVariable Long id) {
		return userMapper.toResponse(
				userRepository.findById(id).orElseThrow(() -> new RuntimeException(Error.USER_NOT_FOUND.getMessage())));
	}

}
