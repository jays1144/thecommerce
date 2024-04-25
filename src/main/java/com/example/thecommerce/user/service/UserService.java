package com.example.thecommerce.user.service;


import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    public ResponseEntity<String> signup(JoinRequestDto requestDto) {

//        String password = passwordEncoder.encode(requestDto.getPassword());
        String password = requestDto.getPassword();


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
}
