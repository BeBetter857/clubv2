package com.hotwave.clubv2;

import com.auth0.jwt.interfaces.Claim;
import com.hotwave.clubv2.entity.User;
import com.hotwave.clubv2.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
class Clubv2ApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setPassword("123456");
        user.setEmail("irving7758520@163.com");
        user.setPhone("18888888888");

        log.info("user" + user);
        Assert.isTrue(user.getId() == "1");
    }
    @Test
    void testUserToken() {
        User user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setPassword("123456");
        user.setEmail("irving7758520@163.com");
        user.setPhone("18888888888");
        String token = JwtUtils.createToken(user.getId(), user.getUsername(), user.getPassword());
        log.info("token:" + token);
        Map<String, Claim> stringClaimMap = JwtUtils.verifyToken(token);

        Set<String> strings = stringClaimMap.keySet();

        for (String string : strings) {
            log.info("key:" + string + " value:" + stringClaimMap.get(string).asString());
        }
    }

}
