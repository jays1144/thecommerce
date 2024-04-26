package com.example.thecommerce.user.security;


import com.example.thecommerce.user.dto.JoinRequestDto;
import com.example.thecommerce.user.entity.UserRoleEnum;
import com.example.thecommerce.user.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성 부분")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil ) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/logins");
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            JoinRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), JoinRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        }catch (IOException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
        String username = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl)auth.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username,role);
        jwtUtil.addJwtToCookie(token,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException fail)  {
        fail.getCause();
        response.setStatus(401);
    }
}
