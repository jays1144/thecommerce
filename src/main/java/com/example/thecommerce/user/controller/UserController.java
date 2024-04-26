package com.example.thecommerce.user.controller;

import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.dto.UserUpdateRequestDto;
import com.example.thecommerce.user.dto.UserListResponseDto;
import com.example.thecommerce.user.security.UserDetailsImpl;
import com.example.thecommerce.user.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api(tags = {"회원"})
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "String", value = "유저아이디", paramType = "query"),
            @ApiImplicitParam(name = "email", dataType = "String", value = "이메일 양식에 맞게 작성해야한다", paramType = "query"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "8~15개 영문 대소문자 특수문자 사용해서 만들어 주세요", paramType = "query"),
            @ApiImplicitParam(name = "nickName", dataType = "String", value = "닉네임", paramType = "query"),
            @ApiImplicitParam(name = "phone", dataType = "String", value = "대한민국 기준의 핸드폰 양식으로 작성해주세요", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "이름", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 201,message = "location link")
    })
    @PostMapping("/join")
    public ResponseEntity<String> signup(@Validated @RequestBody JoinRequestDto requestDto){
        return userService.signup(requestDto);
    }


    @ApiOperation(value = "회원 정보 전체 조회")
    @ApiResponses({
            @ApiResponse(code = 200,message = "회원 전체 정보 paging", response = UserListResponseDto.class)
    })
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

    @ApiOperation(value = "회원 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", dataType = "String", value = "이메일 양식에 맞게 작성해야한다", paramType = "query"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "8~15개 영문 대소문자 특수문자 사용해서 만들어 주세요", paramType = "query"),
            @ApiImplicitParam(name = "nickName", dataType = "String", value = "닉네임", paramType = "query"),
            @ApiImplicitParam(name = "phone", dataType = "String", value = "대한민국 기준의 핸드폰 양식으로 작성해주세요", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "이름", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "회원정보 수정 완료")
    })
    @PutMapping()
    public ResponseEntity<String> updateUser(@Validated @RequestBody UserUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.updateUser(requestDto, userDetails.getUser());
    }
}
