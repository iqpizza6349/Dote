package me.iqpizza6349.dote.domain.dauth.repository;

import me.iqpizza6349.dote.domain.dauth.entity.DToken;
import org.springframework.data.repository.CrudRepository;

public interface DTokenRepository extends CrudRepository<DToken, String> {
}
