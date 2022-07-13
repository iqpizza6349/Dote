package me.iqpizza6349.dote.domain.token.controller;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.token.dto.RefreshTokenDto;
import me.iqpizza6349.dote.domain.token.dto.TokenDto;
import me.iqpizza6349.dote.global.jwt.TokenProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
