package me.iqpizza6349.dote.domain.dauth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class DodamLoginDto {

    @NotBlank(message = "code는 필수 입력값입니다.")
    private String code;
}
