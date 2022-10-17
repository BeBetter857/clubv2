package com.hotwave.clubv2.service;

import com.hotwave.clubv2.dto.UserDto;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.util.ResponseResult;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:42:00
 */

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult registerUser(UserDto userDto);
}
