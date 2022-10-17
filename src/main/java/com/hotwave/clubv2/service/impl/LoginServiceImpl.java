package com.hotwave.clubv2.service.impl;

import com.hotwave.clubv2.controller.UserController;
import com.hotwave.clubv2.dto.UserDto;
import com.hotwave.clubv2.entity.LoginUser;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.mapper.UserMapper;
import com.hotwave.clubv2.service.LoginService;
import com.hotwave.clubv2.util.JwtUtil;
import com.hotwave.clubv2.util.RedisCache;
import com.hotwave.clubv2.util.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:43:00
 */
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {


    private final static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult login(User user) {
        // TODO 使用UsernamePasswordAuthenticationToken 参数为用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        // TODO 使用AuthenticationManager进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        logger.info("loginUser :{}",loginUser.toString());

        String userId = loginUser.getUser().getId();
        // TODO 生成JWT
        String token = JwtUtil.createToken(userId, user.getUserName());

        logger.info("生成的token :{}",token);

        // TODO 将JWT放入Redis中
        redisCache.setCacheObject("token",token);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);

        // TODO 将JWT响应给前段
        ResponseResult responseResult = new ResponseResult(200,"登陆成功",map);

        return responseResult;
    }

    /**
     * 用户注册
     * @param userDto 用户传输对象
     * @return 注册结果
     */
    @Override
    public ResponseResult registerUser(UserDto userDto) {
        logger.info("register_user:{}",userDto);
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        HashMap<String,Object> map = new HashMap<>();

        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUsername());
        try{
            userMapper.insert(user);
            map.put("user",user);
            return new ResponseResult(200,"注册成功",map);
        }catch (Exception e){
            logger.error("插入异常：{}", e);
            map.put("errorMsg",e.getMessage());
            return new ResponseResult(200,"注册失败",map);

        }


    }
}
