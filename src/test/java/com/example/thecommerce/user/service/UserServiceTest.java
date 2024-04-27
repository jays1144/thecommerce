package com.example.thecommerce.user.service;

import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp(){
        JoinRequestDto requestDto = new JoinRequestDto();
        requestDto.setUserId("userId");
        requestDto.setName("name");
        requestDto.setEmail("email@email.com");
        requestDto.setPhone("010-2222-3333");
        requestDto.setPassword("password");
        requestDto.setNickName("nickName");

        user = new User(requestDto,requestDto.getPassword());

    }

    @Test
    void signup() {
        lenient().when(userRepository.save(any(User.class))).thenReturn(user);

        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(user));

        Optional<User> savedUser = userRepository.findByEmail("email@email.com");
        assertTrue(savedUser.isPresent());

        assertEquals("name", savedUser.get().getName());
    }



//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void updateUser() {
//    }
}