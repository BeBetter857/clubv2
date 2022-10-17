package com.hotwave.clubv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotwave.clubv2.entity.LoginUser;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:43:00
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

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

}
