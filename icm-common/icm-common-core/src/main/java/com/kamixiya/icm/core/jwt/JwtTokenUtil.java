package com.kamixiya.icm.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类，用于生成，验证token
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
public final class JwtTokenUtil {

    /**
     * JWT的密钥
     */
    private String secret;

    /**
     * 1周的毫秒数
     */
    private long expiration;

    /**
     * 使用缺省的密钥和时长（1周）构建JwtTokenUtil
     */
    public JwtTokenUtil() {
        this("M1t#c6-S3c$e5", 604800000);
    }

    /**
     * 构造JwtTokenUtil
     *
     * @param secret     加密串
     * @param expiration 有效时长
     */
    public JwtTokenUtil(String secret, long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    /**
     * 从token中读取Id信息
     *
     * @param token token字符串
     * @return id
     */
    public String getIdFromToken(String token) {
        String id = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                id = claims.getId();
            }
        } catch (Exception e) {
            // nothing to do
        }
        return id;
    }

    /**
     * 从token中读取subject(用户名)
     *
     * @param token token字符串
     * @return subject
     */
    public String getSubjectFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                username = claims.getSubject();
            }
        } catch (Exception e) {
            // nothing to do
        }
        return username;
    }

    /**
     * 从token中读取Issue Date (发布时间)
     *
     * @param token token字符串
     * @return issue date
     */
    public Date getIssueDateFromToken(String token) {
        Date issueAt = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                issueAt = claims.getIssuedAt();
            }
        } catch (Exception e) {
            // nothing to do
        }
        return issueAt;
    }

    /**
     * 从token中读取失效时间
     *
     * @param token token字符串
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        Date exp = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                exp = claims.getExpiration();
            }
        } catch (Exception e) {
            // nothing to do
        }
        return exp;
    }

    /**
     * 读取claims
     *
     * @param token token字符串
     * @return claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Token是否已经失效
     *
     * @param token token字符串
     * @return true失效，否则为false
     */
    public Boolean isTokenExpired(String token) {
        Date exp = getExpirationDateFromToken(token);
        return (exp != null && exp.before(new Date()));
    }

    /**
     * 生成token
     *
     * @param claims 信息
     * @return token
     */
    public String generateToken(Map<String, Object> claims) {
        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .setIssuedAt(issueDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token
     *
     * @param id      id（通常可使用用户的id)
     * @param subject 主题(通常可设为用户名)
     * @return token
     */
    public String generateToken(String id, String subject) {
        Map<String, Object> claims = new HashMap<>(10);
        claims.put(Claims.ID, id);
        claims.put(Claims.SUBJECT, subject);
        return generateToken(claims);
    }

    /**
     * 验证token是否有效且未超期
     *
     * @param token token字符串
     * @return true表示验证通过，false未通过验证
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    /**
     * 刷新重新生成token
     *
     * @param tokenOld 旧token字符串
     * @return token
     */
    public String refreshToken(String tokenOld) {
        String token = null;
        Claims claims = getClaimsFromToken(tokenOld);
        if (claims != null) {
            token = generateToken(claims.getId(), claims.getSubject());
        }
        return token;
    }
}
