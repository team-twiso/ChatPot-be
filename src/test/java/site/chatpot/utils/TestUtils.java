package site.chatpot.utils;

import java.time.LocalDate;
import org.springframework.mock.web.MockMultipartFile;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.entity.User;
import site.chatpot.domain.user.entity.enums.Gender;

public class TestUtils {
    private TestUtils() {
    }

    public static UserRegisterResponse userRegisterResponse() {
        return new UserRegisterResponse(1L);
    }

    public static UserRegisterRequest userRegisterRequest() {
        return new UserRegisterRequest(
                "a@naver.com",
                "asdfasdf!@",
                "홍길동",
                "동길홍",
                "1990-01-01",
                Gender.valueOf("MALE"),
                new MockMultipartFile("profile", "test".getBytes())
        );
    }

    public static UserRegisterRequest userRegisterRequestNotImage() {
        return new UserRegisterRequest(
                "a@naver.com",
                "asdfasdf!@",
                "홍길동",
                "동길홍",
                "1990-01-01",
                Gender.valueOf("MALE"),
                null
        );
    }


    public static User user(UserRegisterRequest request, String encodedPassword) {
        return User.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .nickname(request.nickname())
                .birthDate(LocalDate.parse(request.birthDate()))
                .gender(request.gender())
                .build();
    }

    public static Image image() {
        return Image.builder()
                .url("http://localhost:8080/test")
                .build();
    }
}
