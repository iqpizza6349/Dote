package me.iqpizza6349.dote.domain.dauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class DAuthService {

    private final RestTemplateConfig restTemplateConfig;
    private final AppProperties appProperties;
    private final MemberService memberService;
    private final DTokenRepository dTokenRepository;
    private final TokenProvider tokenProvider;

    private DOpenApiDto getCodeToDodamInfo(final String code) {
        log.info("----- dodam server request -----");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "
                + getDAuthToken(code).getAccessToken());
        log.info("header: {}", headers.get("Authorization"));
        return restTemplateConfig.openTemplate().exchange(
                "/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                DOpenApiDto.class
        ).getBody();
    }

    private DAuthServerDto getDAuthToken(@NotNull String code) {
        log.info("----- dauth server request -----");
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
        DOpenApiDto data = getCodeToDodamInfo(dodamLoginDto.getCode());
        Member member = memberService.save(data);
        dTokenRepository.save(new DToken(null, dodamLoginDto.getCode()));
        String memberId = Integer.toString(member.getId());
        return LoginDto.builder()
                .member(member)
                .token(tokenProvider.generateToken(memberId, JwtAuth.ACCESS_TOKEN))
                .refreshToken(tokenProvider.generateToken(memberId, JwtAuth.REFRESH_TOKEN))
                .build();
    }
}
