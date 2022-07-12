package me.iqpizza6349.dote.domain.dauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("token")
public class DToken {

    @Id
    private String id;

    private String token;
}
