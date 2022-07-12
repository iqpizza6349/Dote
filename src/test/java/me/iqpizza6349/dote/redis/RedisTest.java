package me.iqpizza6349.dote.redis;

import me.iqpizza6349.dote.domain.dauth.entity.DToken;
import me.iqpizza6349.dote.domain.dauth.repository.DTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTest {

    @Autowired
    private DTokenRepository dTokenRepository;

    @Test
    void save() {
        // given
        DToken dToken = new DToken(null, "blahblahblah");

        // when
        DToken savedDToken = dTokenRepository.save(dToken);

        // then
        Optional<DToken> findDToken = dTokenRepository.findById(savedDToken.getId());

        assertThat(findDToken.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findDToken.get().getToken()).isEqualTo("blahblahblah");
    }
}
