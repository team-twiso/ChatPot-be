package site.chatpot.domain.user.converter;

import java.time.LocalDate;
import java.util.Optional;
import site.chatpot.domain.common.annotation.Converter;
import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserLoginResponse;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.entity.User;

@Converter
public class UserConverter {

    public User toEntity(UserRegisterRequest request, Image image, String encodedPassword) {
        return Optional.ofNullable(request)
                .map(it -> User.builder()
                        .email(request.email())
                        .password(encodedPassword)
                        .name(request.name())
                        .nickname(request.nickname())
                        .birthDate(LocalDate.parse(request.birthDate()))
                        .gender(request.gender())
                        .image(image)
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.COMMON_INVALID_PARAMETER));
    }

    public UserRegisterResponse toResponse(User user) {
        return Optional.ofNullable(user)
                .map(it -> new UserRegisterResponse(
                        user.getId()))
                .orElseThrow(() -> new ApiException(ErrorCode.COMMON_SYSTEM_ERROR, "User Entity가 존재하지 않습니다."));
    }

    public UserLoginResponse toLoginResponse(String accessToken, String refreshToken) {
        return new UserLoginResponse(accessToken, refreshToken);
    }
}
