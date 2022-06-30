package com.hotwave.clubv2.entity;

import lombok.Data;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-06-29 20:44:00
 */
@Data
public class User extends BaseEntity {

    private String username;

    private String password;

    private String email;

    private String phone;

    private String nickname;

}
