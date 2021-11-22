package com.example.userservice.Jwt;

import com.example.userservice.common.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtServiceImpl {
    private String secretKey = "lg-uplus-team3-secret-key-lg-uplus-team3-secret-key"; // 서명에 사용할 secretKey
    private long exp = 1000L * 60 * 60; // 토큰 사용가능 시간, 1시간

    // 토큰 생성하는 메서드
    public String createToken(UserDto user) { // 토큰에 담고싶은 값 파라미터로 가져오기
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .setSubject("userToken") // 토큰 제목
                .setExpiration(new Date(System.currentTimeMillis() + exp)) // 토큰 유효시간
                .claim("user", user) // 토큰에 담을 데이터
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    // 토큰에 담긴 정보를 가져오기 메서드
    public Map<String, Object> getInfo(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token); // secretKey를 사용하여 복호화
        return claims.getBody();
    }

}
