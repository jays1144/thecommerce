package com.example.thecommerce.user.service;


import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.entity.User;
import com.example.thecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
//        String password = requestDto.getPassword();


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

//    public Page<ProductResponseDto> getProducts(int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
//        // pageSize가 한번에 몇개를 넘겨줄지
//        // pageNumber는 현재 페이지의 위치 값
//        // direction : 정렬을 오름차순으로할지 내림차순으로 할지 결정
//        // sortBy : 정렬기준
//        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, direction, sortBy);
//
//        Page<Product> products = productRepository.findAll(pageRequest);
//
//        return products.map(ProductResponseDto::new);
//    }

    public Page<UserListResponseDto> getAll(int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, direction, sortBy);

        Page<User> users = userRepository.findAll(pageRequest);

        return users.map(UserListResponseDto::new);


//        return products.map(ProductResponseDto::new);
    }
}
