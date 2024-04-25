package com.example.thecommerce.user.entity;

import com.example.thecommerce.user.dto.JoinRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    public User(JoinRequestDto requestDto,String password){
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.password = password;
        this.phone = requestDto.getPhone();
        this.nickName = requestDto.getNickName();
        this.userId = requestDto.getUserId();
    }
}
