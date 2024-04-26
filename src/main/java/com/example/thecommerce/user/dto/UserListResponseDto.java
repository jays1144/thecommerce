package com.example.thecommerce.user.dto;

import com.example.thecommerce.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserListResponseDto {

    @ApiModelProperty(example = "name")
    private String name;
    @ApiModelProperty(example = "jaiws")
    private String userId;
    @ApiModelProperty(example = "nick")
    private String nickName;
    @ApiModelProperty(example = "010-2223-3424")
    private String phone;


    public UserListResponseDto(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.phone = user.getPhone();
    }
}
