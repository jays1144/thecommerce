package com.example.thecommerce.user.jwt;

import com.example.thecommerce.user.msg.ErrorMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 10000L;

//    public static final String JWT_LOG_HEAD = "JWT 관련 로그";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtil.class);

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email) // 사용자 식별자값(ID)
//                        .claim(AUTHORIZATION_KEY, "none") // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/"); // 해당 쿠키가 어떤요청일때 같이 넘어가는지 설정 현재 상태는 모든요청에서 같이 넘어간다

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }


    // cookie에서 jwt를 사용하는 경우
    public String getTokenFromCookie(HttpServletRequest req) {
        javax.servlet.http.Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        String decodeCookie = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        return decodeCookie.substring(7);
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error(ErrorMessage.INVALID_JWT_ERROR_MESSAGE.getErrorMessage());
        } catch (ExpiredJwtException e) {
            logger.error(ErrorMessage.EXPIRED_JWT_ERROR_MESSAGE.getErrorMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(ErrorMessage.UNSUPPORTED_JWT_ERROR_MESSAGE.getErrorMessage());
        } catch (IllegalArgumentException e) {
            logger.error(ErrorMessage.EMPTY_JWT_ERROR_MESSAGE.getErrorMessage());
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
