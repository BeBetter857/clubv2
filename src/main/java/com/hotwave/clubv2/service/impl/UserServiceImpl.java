package com.hotwave.clubv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotwave.clubv2.entity.LoginUser;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.mapper.UserMapper;
import com.hotwave.clubv2.service.UserService;
import com.hotwave.clubv2.util.JwtUtils;
import com.hotwave.clubv2.util.RedisCache;
import com.hotwave.clubv2.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:43:00
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);

        User user = userMapper.selectOne(wrapper);

        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        // TODO 查询用户信息封装到LoginUser中

        System.out.printf("user", user);

        //TODO 根据用户查询权限信息 添加到LoginUser中
        List<String> list = new ArrayList<>(Arrays.asList("test"));

        // TODO 封装为UserDetails对象返回
        return new LoginUser(user,list);
    }

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
        String userId = loginUser.getUser().getId();
        // TODO 生成JWT
        String token = JwtUtils.createToken(userId, user.getUserName(), user.getPassword());

        System.out.printf("TOKEN", token);

        // TODO 将JWT放入Redis中
        redisCache.setCacheObject("token",token);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);

        // TODO 将JWT响应给前段
        ResponseResult responseResult = new ResponseResult(200,"登陆成功",map);

        return responseResult;
    }
}
