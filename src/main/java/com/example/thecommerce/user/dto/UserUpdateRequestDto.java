package com.example.thecommerce.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateRequestDto {

    @ApiModelProperty(example = "email@email.com")
    @Email
    private String email;

    @ApiModelProperty(example = "Al23!dfw4")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @ApiModelProperty(example = "nick")
    private String nickName;

    @ApiModelProperty(example = "010-2232-1234")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 핸드폰 번호 형식이 아닙니다.")
    private String phone;

    @ApiModelProperty(example = "name")
    private String name;
}
