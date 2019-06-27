package com.zhiku.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhiku.entity.User;
import com.zhiku.exception.TokenVerifyErrorException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT（JSON Web Tokens）工具类
 * 用于生成用户登录token
 * 关于token验证详情，可进入下面的网站了解
 * https://ninghao.net/blog/2834
 */
public class JWTUtil {
    public static final String SECRET = "sharingzhiku";     //秘钥
    public static final int calendarField = Calendar.HOUR;
    public static final int maxAge = 3;

    public static  String signToken(User user) throws Exception{
        Date issueTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueTime);
        calendar.add(calendarField,maxAge);     //token过期时间设置为3小时
        Date expireTime = calendar.getTime();
        Map<String ,Object> header = new HashMap<String,Object>();
        header.put("alg","HS256");
        header.put("type","JWT");
        String token = JWT.create().withHeader(header) //设置头部信息
                .withClaim("iss","Service")
                .withClaim("aud","Web")
                .withClaim("uid",user.getUid()) //添加uid到token中
                .withClaim("username",user.getUserUsername())   //添加username到token中
                .withClaim("useremail",user.getUserEmail())     //添加useremail到token中
                .withIssuedAt(issueTime)    //设置token签发时间
                .withExpiresAt(expireTime)  //设置token过期时间
                .sign(Algorithm.HMAC256(SECRET));       //设置加密签发

        return token;
    }

    /**
     * 验证token并解析签发token时的键值对信息
     * 主要是想解析出签发时的（uid，username，useremail）
     * @param token
     * @return token中的键值对信息
     * @throws TokenVerifyErrorException token验证失败
     */
    public static Map<String, Claim> verifyToken(String token) throws TokenVerifyErrorException {
        try{
            DecodedJWT jwt = null;
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = jwtVerifier.verify(token);
            return jwt.getClaims();
        }catch (Exception e){
            throw new TokenVerifyErrorException("登录信息验证失败！");
        }
    }

    /**
     * 验证邮箱并获取用户名
     * @param token
     * @return username
     * @throws TokenVerifyErrorException
     */
    public static String getUserName(String token) throws TokenVerifyErrorException {
        Map<String,Claim> claims = verifyToken(token);
        Claim uid_claim = claims.get("username");
        return uid_claim.asString();
    }

    /**
     * 验证token并获取uid
     * @param token
     * @return uid
     * @throws TokenVerifyErrorException
     */
    public static int getUid(String token) throws TokenVerifyErrorException {
        Map<String,Claim> claims = verifyToken(token);
        Claim uid_claim = claims.get("uid");
        return uid_claim.asInt();
    }

    //开发时用来测试的，现在没啥用，可以删除
    public static void main(String[] args)throws Exception{
        User user = new User();
        user.setUid(1);
        user.setUserEmail("1368183370@qq.com");
        user.setUserUsername("baowei");
        String token = signToken(user);
        System.out.println(token);
    }
}
