package me.iqpizza6349.dote.domain.token.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class RefreshTokenDto {

    @JsonProperty("refresh_token")
    @NotBlank(message = "refresh token은 필수 입력값입니다.")
    private String refreshToken;
}
