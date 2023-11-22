package com.example.springsecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JWTSimpleTest {
    private final String SECRET_KEY = "minseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseokminseok";
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    @DisplayName("jjwt를 이용한 토큰 테스트")
    @Test
    void test1() {
        //encode
        String okta_token = Jwts.builder()
                .addClaims(
                        Map.of("name", "minseok", "price", 3000))
                .signWith(getSignKey(SECRET_KEY))
                .compact();
        System.out.println("okta_token = " + okta_token);
        printToken(okta_token);

        //decode
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(getSignKey(SECRET_KEY))
                .build()
                .parseClaimsJws(okta_token);
        System.out.println("jws = " + jws);
    }

    @DisplayName("java-jwt를 이용한 토큰 테스트")
    @Test
    void test2() {
        //encode
        String auth0_token = JWT.create()
                .withClaim("name", "minseok")
                .withClaim("price", 3000)
                .sign(ALGORITHM);
        System.out.println("auth0_token = " + auth0_token);
        printToken(auth0_token);

        //decode
        DecodedJWT verified = JWT.require(ALGORITHM)
                .build()
                .verify(auth0_token);
        System.out.println("verified = " + verified);

        //jjwt의 디코딩 방법이 적용 가능
        /*Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(getSignKey(SECRET_KEY))
                .build()
                .parseClaimsJws(auth0_token);
        System.out.println("jws = " + jws);*/
    }

    @DisplayName("만료 시간 테스트")
    @Test
    void test3() throws InterruptedException {
        //encode
        String token = JWT.create()
                .withSubject("user")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                .sign(ALGORITHM);

        //2초 대기
        Thread.sleep(2000);

        //decode : 토큰 만료
        DecodedJWT verified = JWT.require(ALGORITHM)
                .build()
                .verify(token);
    }

    @DisplayName("유효 시간 테스트")
    @Test
    void test4() {
        //encode
        String token = JWT.create()
                .withSubject("user")
                .withExpiresAt(new Date(System.currentTimeMillis() + 2000))
                .withNotBefore(new Date(System.currentTimeMillis() + 1000))
                .sign(ALGORITHM);

        //decode : 토큰이 유효하지 않음(1초가 안지났음)
        DecodedJWT verified = JWT.require(ALGORITHM)
                .build()
                .verify(token);
    }

    private void printToken(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header : " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body : " + new String(Base64.getDecoder().decode(tokens[1])));
    }

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}