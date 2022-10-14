package com.hotwave.clubv2.service;

import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.util.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:42:00
 */

public interface UserService {

    ResponseResult login(User user);
}
