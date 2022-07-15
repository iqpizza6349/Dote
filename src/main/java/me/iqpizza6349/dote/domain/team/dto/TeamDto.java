package me.iqpizza6349.dote.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class TeamDto {

    @NotNull(message = "항목의 이름은 필수입니다.")
    private String name;

}
