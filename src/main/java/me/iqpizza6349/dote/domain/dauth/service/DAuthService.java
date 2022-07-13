package me.iqpizza6349.dote.domain.dauth.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.dauth.dto.*;
import me.iqpizza6349.dote.domain.dauth.entity.DToken;
import me.iqpizza6349.dote.domain.dauth.repository.DTokenRepository;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.service.MemberService;
import me.iqpizza6349.dote.global.config.AppProperties;
import me.iqpizza6349.dote.global.enums.JwtAuth;
import me.iqpizza6349.dote.global.jwt.TokenProvider;
import me.iqpizza6349.dote.global.resttemplate.RestTemplateConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class DAuthService {

    private final RestTemplateConfig restTemplateConfig;
    private final AppProperties appProperties;
    private final MemberService memberService;
    private final DTokenRepository dTokenRepository;
    private final TokenProvider tokenProvider;

    private DOpenApiDto getCodeToDodamInfo(final String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer "
                + getDAuthToken(code).getAccessToken());
        return restTemplateConfig.openTemplate().exchange(
                "/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                DOpenApiDto.class
        ).getBody();
    }

    private DAuthServerDto getDAuthToken(@NotNull String code) {
        return restTemplateConfig.authTemplate()
                .postForObject("/token", new HttpEntity<>(
                        DAuthRequestDto.builder()
                                .code(code)
                                .clientId(appProperties.getClientId())
                                .clientSecret(appProperties.getClientSecret())
                                .build(),
                        null
                ), DAuthServerDto.class);
    }

    @Transactional
    public LoginDto dodamLogin(DodamLoginDto dodamLoginDto) {
        Member member = memberService.save(getCodeToDodamInfo(dodamLoginDto.getCode()));
        dTokenRepository.save(new DToken(null, dodamLoginDto.getCode()));
        String  memberId = Integer.toString(member.getId());
        return LoginDto.builder()
                .member(member)
                .token(tokenProvider.generateToken(memberId, JwtAuth.ACCESS_TOKEN))
                .refreshToken(tokenProvider.generateToken(memberId, JwtAuth.REFRESH_TOKEN))
                .build();
    }
}
