package com.example.thecommerce.user.service;


import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.dto.UserResponseDto;
import com.example.thecommerce.user.dto.UserUpdateRequestDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "userService")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public ResponseEntity<String> signup(JoinRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> isValid = userRepository.findByEmail(requestDto.getEmail());
        if (isValid.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }

        User user = new User(requestDto, password);

        userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public Page<UserListResponseDto> getAll(int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, direction, sortBy);

        Page<User> users = userRepository.findAll(pageRequest);

        return users.map(UserListResponseDto::new);
    }

    @Transactional
    public ResponseEntity<String> updateUser(UserUpdateRequestDto requestDto,User user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("가입되어있지 않는 회원입니다 다시 확인해주세요"));

        findUser.update(requestDto);

        return ResponseEntity.ok("회원정보가 변경되었습니다");
    }
}
