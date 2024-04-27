package com.example.thecommerce.user.service;

import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.dto.UserUpdateRequestDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.entity.UserRoleEnum;
import com.example.thecommerce.user.repository.UserRepository;
import com.example.thecommerce.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void signup_Success() {
        // Given
        JoinRequestDto requestDto = JoinRequestDto.builder()
                .userId("userId")
                .phone("010-2222-3333")
                .email("newuser@example.com")
                .name("name")
                .nickName("nickName")
                .password("password")
                .build();

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
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(user.getId())
//                .toUri();

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");

        // When
        ResponseEntity<String> response = userService.signup(requestDto);

        // Then
        verify(userRepository).save(any(User.class));
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

//    @Test
//    void getAllUsers() {
//        // Given
//        int pageNumber = 0;
//        int pageSize = 10;
//        String sortBy = "userId";
//        Page<User> mockedPage = mock(Page.class);
//
//        when(userRepository.findAll(any())).thenReturn(mockedPage);
//
//        // When
//        Page<UserListResponseDto> result = userService.getAll(pageNumber, pageSize, sortBy);
//
//        // Then
//        assertNotNull(result);
//        verify(userRepository).findAll(any());
//    }

    @Test
    void updateUser_Success() {
        // Given
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder().name("changeName").build();

        User user = User.builder().id(1L).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        ResponseEntity<String> response = userService.updateUser(requestDto, user);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("회원정보가 변경되었습니다", response.getBody());
    }

    @Test
    void updateUser_UserNotFound() {
        // Given
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder().name("changeName").build();
        User user = User.builder().id(1L).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(requestDto, user));
    }
}
