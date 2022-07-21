package me.iqpizza6349.dote.domain.dauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.type.Role;

import java.io.Serializable;

@Slf4j
@Getter
@NoArgsConstructor
public class DOpenApiDto implements Serializable {

    @JsonProperty("data")
    private DodamInfoData dodamInfoData;

    public DOpenApiDto(DodamInfoData dodamInfoData) {
        this.dodamInfoData = new DodamInfoData(dodamInfoData);
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class DodamInfoData implements Serializable {
        private String uniqueId;
        private int grade;
        private int room;
        private int number;
        private String name;
        private String email;
        private String profileImage;
        private int accessLevel;

        public DodamInfoData(DodamInfoData data) {
            this.uniqueId = data.getUniqueId();
            this.grade = data.getGrade();
            this.room = data.getRoom();
            this.number = data.getNumber();
            this.name = data.getName();
            this.email = data.getEmail();
            this.profileImage = data.getProfileImage();
            this.accessLevel = data.getAccessLevel();
        }

        public static Member toEntity(DodamInfoData data) {
            Role role = Role.USER;
            if (data.getGrade() == 2 && data.getRoom() == 1 && data.getNumber() == 17) {
                role = Role.ADMIN;
            }
            if (data.getGrade() == 2 && data.getRoom() == 2 && data.getNumber() == 18) {
                role = Role.ADMIN;
            }
            if (data.getGrade() == 2 && data.getRoom() == 2 && data.getNumber() == 17) {
                role = Role.ADMIN;
            }
            log.info("role: {}", role);

            return Member.builder()
                    .grade(data.getGrade())
                    .number(data.getNumber())
                    .room(data.getRoom())
                    .role(role)
                    .build();
        }
    }
}
