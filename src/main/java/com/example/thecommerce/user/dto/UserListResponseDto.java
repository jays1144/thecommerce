package com.example.thecommerce.user.dto;

import com.example.thecommerce.user.entity.User;
import lombok.Getter;

@Getter
public class UserListResponseDto {

    private String name;
    private String userId;
    private String nickName;
    private String phone;


    public UserListResponseDto(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.phone = user.getPhone();
    }
}
