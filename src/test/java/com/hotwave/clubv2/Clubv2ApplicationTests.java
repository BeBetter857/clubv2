package com.hotwave.clubv2;

import com.hotwave.clubv2.controller.UserController;
import com.hotwave.clubv2.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootTest
class Clubv2ApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void test1() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey originalKey = keyGenerator.generateKey();

        byte[] rawData = originalKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        System.out.println("encodedKey" + encodedKey);

        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey1 = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        System.out.printf("...", originalKey1);
    }

    @Test
    public void test2(){
        String encode = passwordEncoder.encode("7758520");
        logger.info(encode);
    }

}
