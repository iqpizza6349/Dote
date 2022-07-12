package me.iqpizza6349.dote.domain.dauth.controller;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.dauth.dto.DodamLoginDto;
import me.iqpizza6349.dote.domain.dauth.dto.LoginDto;
import me.iqpizza6349.dote.domain.dauth.service.DAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class DAuthController {

    private final DAuthService dAuthService;

    @PostMapping("/login")
    public LoginDto login(@RequestBody @Valid final DodamLoginDto dodamLoginDto) {
        return dAuthService.dodamLogin(dodamLoginDto);
    }
}
