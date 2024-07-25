package site.chatpot.domain.user.entity;

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
}
