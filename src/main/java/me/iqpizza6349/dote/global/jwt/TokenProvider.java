package me.iqpizza6349.dote.global.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.repository.MemberRepository;
import me.iqpizza6349.dote.global.config.AppProperties;
import me.iqpizza6349.dote.global.enums.JwtAuth;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private static final long JWT_ACCESS_EXPIRE = 1000 * 60 * 60;
    private static final long JWT_REFRESH_EXPIRE = 1000 * 60 * 60 * 7;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private final AppProperties appProperties;
    private final MemberRepository memberRepository;

    public String generateToken(String userId, JwtAuth jwtAuth) {
        Date expiredAt = new Date();
        expiredAt = (jwtAuth == JwtAuth.ACCESS_TOKEN)
                ? new Date(expiredAt.getTime() + JWT_ACCESS_EXPIRE)
                : new Date(expiredAt.getTime() + JWT_REFRESH_EXPIRE);
        String secretKey = (jwtAuth == JwtAuth.ACCESS_TOKEN)
                ? appProperties.getSecret()
                : appProperties.getRefreshSecret();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(jwtAuth.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiredAt)
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    private Claims parseToken(String token, JwtAuth jwtAuth) {
        try {
            return Jwts.parser()
                    .setSigningKey((jwtAuth == JwtAuth.ACCESS_TOKEN)
                    ? appProperties.getSecret()
                    : appProperties.getRefreshSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "????????? ?????????????????????.");
        } catch (SignatureException | MalformedJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "????????? ???????????????.");
        } catch (IllegalArgumentException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "????????? ???????????? ????????????.");
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "?????? ??????????????? ????????? ?????????????????????.");
        }
    }

    public Member validateToken(String token) {
        return memberRepository.findById(
                Integer.valueOf(parseToken(token, JwtAuth.ACCESS_TOKEN)
                        .get("userId")
                        .toString())
        )
                .orElseThrow(Member.NotFoundException::new);
    }

    public String refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "????????? ???????????? ????????????.");
        }

        Claims claims = parseToken(refreshToken, JwtAuth.REFRESH_TOKEN);
        Member member = memberRepository
                .findById(Integer.parseInt(claims.get("userId").toString()))
                .orElseThrow(Member.NotFoundException::new);
        return generateToken(member.getId().toString(), JwtAuth.ACCESS_TOKEN);
    }
}
