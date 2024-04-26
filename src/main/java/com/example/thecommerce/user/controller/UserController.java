package com.example.thecommerce.user.controller;

import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> signup(@RequestBody JoinRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserListResponseDto>> getProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy, // 추가: 선택적으로 정렬 기준을 받을 수 있음
            @RequestParam(defaultValue = "DESC") String sortOrder // 정렬기준
    ) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Page<UserListResponseDto> products = userService.getAll(pageNumber, pageSize, sortBy,direction);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
