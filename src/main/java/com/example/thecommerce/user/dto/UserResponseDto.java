package com.example.thecommerce.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserResponseDto {

    @ApiModelProperty(example = "name")
    private String name;
    @ApiModelProperty(example = "jwods")
    private String userId;
    @ApiModelProperty(example = "nick")
    private String nickName;
    @ApiModelProperty(example = "010-2223-2434")
    private String phone;
}
