package com.hotwave.clubv2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-30 20:58:00
 */
@Slf4j
public class JwtUtils {
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 过期时间（单位是毫秒）
    private static final String SECRET = "UUID202206300900"; // 密钥


    /**
     * 创建用户Token
     * @param id 用户Id
     * @param username 用户名
     * @param password  密码
     * @return 用户 token
     */
    public static String createToken(String id, String username,String password) {

        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME); // 在当前时间基础上加上过期时间

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256"); // 指使用的签名算法，如HMAC、SHA256或RSA。
        headerMap.put("typ", "JWT"); // 用于声明token的类型，这里是JWT。

        String token = JWT.create()
                .withHeader(headerMap) // 设置头部信息
                .withClaim("id", id) // 基本信息
                .withClaim("username", username)
                .withClaim("password", password)
                .withIssuer("clubv2") // 签发者
                .withAudience(id) // 接收者
                .withIssuedAt(new Date()) // 生成签发时间
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(SECRET)); // 加密
        return token;
    }
    /**
     * 解析token并校验token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        }catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }
        return jwt.getClaims();
    }

    /**
     * 判断Token是否过期
     */
    public static Boolean isExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    /**
     * 判断Token授予的和用户是否一致
     */
    public static Boolean isSameUser(String token,String id) {
        return JWT.decode(token).getAudience().equals(id);
    }


}
