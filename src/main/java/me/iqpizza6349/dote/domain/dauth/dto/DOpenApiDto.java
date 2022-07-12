package me.iqpizza6349.dote.domain.dauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.global.dto.RestBaseDto;

@Getter
public class DOpenApiDto extends RestBaseDto {

    private final DodamInfoData dodamInfoData;

    public DOpenApiDto(int status, String message, DodamInfoData dodamInfoData) {
        super(status, message);
        this.dodamInfoData = new DodamInfoData(dodamInfoData);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class DodamInfoData {
        private final String uniqueId;
        private final int grade;
        private final int room;
        private final int number;
        private final String name;
        private final String email;
        private final String profileImage;
        private final int accessLevel;

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
            return Member.builder()
                    .grade(data.getGrade())
                    .number(data.getNumber())
                    .room(data.getRoom())
                    .build();
        }
    }
}
