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
    public ResponseEntity<Page<UserListResponseDto>> getUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder
    ) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String sorting = sortBy.equals("id") ? "id" : "createAt";

        Page<UserListResponseDto> users = userService.getAll(pageNumber, pageSize, sorting,direction);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
