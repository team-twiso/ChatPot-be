package site.chatpot.domain.user.entity.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    ;

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static String errorMessage() {
        return "MALE 또는 FEMALE 로 입력해 주세요.";
    }
}
