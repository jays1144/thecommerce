package com.example.thecommerce.user.controller;

import com.example.thecommerce.user.controller.UserController;
import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.dto.UserUpdateRequestDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.entity.UserRoleEnum;
import com.example.thecommerce.user.security.UserDetailsImpl;
import com.example.thecommerce.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.initMocks(this);
    }
    @Test
    void signupTest() {
        // Given
        JoinRequestDto joinRequestDto = new JoinRequestDto();

        // When
        when(userService.signup(any(JoinRequestDto.class)))
                .thenReturn(new ResponseEntity<>("회원가입성공", HttpStatus.CREATED));

        ResponseEntity<String> responseEntity = userController.signup(joinRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("회원가입성공", responseEntity.getBody());
    }

    @Test
    void getUsersTest() {
        // Given
        Page<UserListResponseDto> userListResponseDtoPage = mock(Page.class);

        // When
        when(userService.getAll(0, 5, "id", Sort.Direction.valueOf("DESC")))
                .thenReturn(userListResponseDtoPage);

        User user = User.builder()
                .id(1L)
                .userId("userId")
                .role(UserRoleEnum.USER)
                .password("password")
                .phone("010-2222-3333")
                .email("email@email.com")
                .name("name")
                .nickName("nickName")
                .build();

        User user2 = User.builder()
                .id(2L)
                .userId("userId2")
                .role(UserRoleEnum.USER)
                .password("password")
                .phone("010-2222-3333")
                .email("email2@email.com")
                .name("name2")
                .nickName("nickName")
                .build();

        User user3 = User.builder()
                .id(3L)
                .userId("userId3")
                .role(UserRoleEnum.USER)
                .password("password")
                .phone("010-2222-3333")
                .email("email3@email.com")
                .name("name3")
                .nickName("nickName")
                .build();

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "userId"));

        Page<User> page = new PageImpl<>(users, pageable, users.size());

        ResponseEntity<Page<User>> response = new ResponseEntity<>(page, HttpStatus.OK);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void updateUserTest() {
        // Given
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        // When
        when(userService.updateUser(userUpdateRequestDto, userDetails.getUser()))
                .thenReturn(new ResponseEntity<>("회원정보 수정 완료", HttpStatus.OK));

        ResponseEntity<String> responseEntity = userController.updateUser(userUpdateRequestDto, userDetails);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("회원정보 수정 완료", responseEntity.getBody());
    }
}
