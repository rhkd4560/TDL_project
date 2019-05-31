package com.gwangmin.tdl.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    @NotBlank(message = "email을 입력하세요.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @Size(min = 1, max = 20)
    private String email;

    @NotBlank(message = "password를 입력하세요.")
    @Size(min = 4, max = 12)
    private String password;

    public User user(){
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }


}
