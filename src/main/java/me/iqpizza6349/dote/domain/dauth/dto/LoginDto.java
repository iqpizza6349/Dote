package me.iqpizza6349.dote.domain.dauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.iqpizza6349.dote.domain.member.entity.Member;

@Getter
@AllArgsConstructor
@Builder
public class LoginDto {
    private Member member;
    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
