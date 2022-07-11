package me.iqpizza6349.dote.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionDto {

    @JsonProperty(value = "timestamp", index = 0)
    private final LocalDateTime timeStamp = LocalDateTime.now();

    private final String message;
}
