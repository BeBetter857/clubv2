package com.hotwave.clubv2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hotwave.clubv2.entity.User;
import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;


/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-30 20:58:00
 */

public class JwtUtil {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 有效期（单位是毫秒）
    private static final long JWT_TTL =  60 * 60 * 24 * 7;

    // 明文密钥
    public static final String SECRET_KEY = "UUID202206300900";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    public static String createToken(String id, String username) {
        JwtBuilder builder = getJwtBuilder(id, username,null);
        logger.info("Token",builder.compact());
        return builder.compact();
    }

    public static String createToken(String id, String username,Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(id, username, ttlMillis);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String id, String username,Long ttlMillis) {
        SecretKey secretKey = generalKey();

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256"); // 指使用的签名算法，如HMAC、SHA256或RSA。
        headerMap.put("typ", "JWT"); // 用于声明token的类型，这里是JWT。

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis == null || ttlMillis < 0){
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setHeader(headerMap)// 设置头部信息
                .setId(id)              //唯一的ID
                .setSubject(username)   // 主题  可以是JSON数据
                .setIssuer("clubv2")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }


    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.SECRET_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析
     * @param token
     * @return
     * @throws Exception
     */
    public static Claims getTokenBody(String token)  {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }


    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }


    }
