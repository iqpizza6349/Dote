package me.iqpizza6349.dote.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.member.type.Role;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int grade;

    private int room;

    private int number;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static class NotFoundException extends BusinessException {
        public NotFoundException() {
            super(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
    }

}
