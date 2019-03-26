package com.zhiku.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhiku.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    public static final String SECRET = "sharingzhiku";
    public static final int calendarField = Calendar.HOUR;
    public static final int maxAge = 3;

    public static  String signToken(User user) throws Exception{
        Date issueTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueTime);
        calendar.add(calendarField,maxAge);
        Date expireTime = calendar.getTime();
        Map<String ,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("type","JWT");
        String token = JWT.create().withHeader(header)
                .withClaim("iss","Service")
                .withClaim("aud","Web")
                .withClaim("uid",user.getUid())
                .withClaim("username",user.getUserUsername())
                .withClaim("useremail",user.getUserEmail())
                .withIssuedAt(issueTime)
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = null;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = jwtVerifier.verify(token);
        return jwt.getClaims();
    }

    public static int getUid(String token) throws UnsupportedEncodingException{
        Map<String,Claim> claims = verifyToken(token);
        Claim uid_claim = claims.get("uid");
        return uid_claim.asInt();
    }

    public static void main(String[] args)throws Exception{
        User user = new User();
        user.setUid(10000);
        user.setUserEmail("1368183370@qq.com");
        user.setUserUsername("baowei");
        String token = signToken(user);
        System.out.println(token);
    }
}
