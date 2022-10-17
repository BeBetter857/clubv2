package com.hotwave.clubv2.controller;

import com.hotwave.clubv2.dto.UserDto;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.service.LoginService;
import com.hotwave.clubv2.util.ResponseResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:52:00
 */
@Api("用户管理")
@RestController()
@RequestMapping("api/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        logger.info("user : {} 名称"  , user.getUserName());
        return loginService.login(user);
    }

    @PostMapping("/register")
    public ResponseResult registerUser(@Valid @RequestBody UserDto userDto, BindingResult result){
        if (result.hasErrors()) {
            HashMap map = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.stream().forEach(e -> logger.info("error :{}",e));
            if(!fieldErrors.isEmpty()){
                map.put("data",fieldErrors.get(0).getDefaultMessage());
                return new ResponseResult(400,"注册失败",map);
            }
        }
        logger.info("userDto :{} " ,userDto);
        return loginService.registerUser(userDto);
    }

}
