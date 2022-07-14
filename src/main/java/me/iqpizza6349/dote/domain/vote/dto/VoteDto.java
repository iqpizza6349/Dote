package me.iqpizza6349.dote.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.team.dto.TeamDto;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class VoteDto {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @Valid
    @NotNull(message = "항목은 2개 이상 등록되어야합니다.")
    @Size(min = 2, message = "항목은 2개 이상 등록되어야합니다.")
    private List<TeamDto> items;

    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Nullable
    private LocalDateTime endTime;

}
