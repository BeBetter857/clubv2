package com.hotwave.clubv2;

import com.hotwave.clubv2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@Slf4j
class Clubv2ApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setEmail("irving7758520@163.com");
        user.setPhone("18888888888");

        log.info("user" + user);
        Assert.isTrue(user.getId() == 1);
    }

}
