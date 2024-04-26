package com.example.thecommerce.user.entity;

import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped{

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(JoinRequestDto requestDto,String password){
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.password = password;
        this.phone = requestDto.getPhone();
        this.nickName = requestDto.getNickName();
        this.userId = requestDto.getUserId();
        this.role = UserRoleEnum.USER;
    }

    public void update(UserUpdateRequestDto requestDto) {
        if (requestDto.getEmail() != null){
            this.email = requestDto.getEmail();
        }
        if (requestDto.getName() != null){
            this.name = requestDto.getName();
        }
        if (requestDto.getPhone() != null){
            this.phone = requestDto.getPhone();
        }
        if (requestDto.getPassword() != null){
            this.password = requestDto.getPassword();
        }
        if (requestDto.getNickName() != null){
            this.nickName = requestDto.getNickName();
        }
    }
}
