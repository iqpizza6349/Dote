package me.iqpizza6349.dote.domain.token.controller;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.dauth.dto.LoginDto;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.token.dto.RefreshTokenDto;
import me.iqpizza6349.dote.domain.token.dto.TokenDto;
import me.iqpizza6349.dote.global.enums.JwtAuth;
import me.iqpizza6349.dote.global.jwt.TokenProvider;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenProvider tokenProvider;

    @PostMapping("/refresh")
    public TokenDto refreshToken(@RequestBody @Valid final RefreshTokenDto refreshTokenDto) {
        return new TokenDto(tokenProvider.refreshToken(
                refreshTokenDto.getRefreshToken()),
                "토큰이 재발급되었습니다."
        );
    }

    @GetMapping
    public LoginDto refreshToken() {
        return new LoginDto(null,
                tokenProvider.generateToken("1", JwtAuth.ACCESS_TOKEN),
                tokenProvider.generateToken("1", JwtAuth.REFRESH_TOKEN)
        );
    }
}
