package com.hotwave.clubv2.dto;


import com.hotwave.clubv2.anno.PasswordMatches;
import com.hotwave.clubv2.anno.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-10-16 23:30:00
 */

@PasswordMatches
@Data
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repeatedPassword;

    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

}
