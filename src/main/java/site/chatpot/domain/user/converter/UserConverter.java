package site.chatpot.domain.user.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import site.chatpot.domain.common.annotation.Converter;
import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserLoginResponse;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.controller.response.UserResponse;
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

    public UserRegisterResponse toRegisterResponse(User user) {
        return Optional.ofNullable(user)
                .map(it -> new UserRegisterResponse(
                        user.getId()))
                .orElseThrow(() -> new ApiException(ErrorCode.COMMON_SYSTEM_ERROR, "User Entity가 존재하지 않습니다."));
    }

    public UserLoginResponse toLoginResponse(String accessToken, String refreshToken) {
        return new UserLoginResponse(accessToken, refreshToken);
    }

    public UserResponse toResponse(User user) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(user)
                .map(it -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getNickname(),
                        user.getBirthDate().format(dateFormatter),
                        user.getGender().name(),
                        Optional.ofNullable(user.getImage()).map(Image::getUrl).orElse(null),
                        user.getCreatedAt().format(dateTimeFormatter),
                        user.getLastModifiedAt().format(dateTimeFormatter)))
                .orElseThrow(() -> new ApiException(ErrorCode.COMMON_SYSTEM_ERROR, "User Entity가 존재하지 않습니다."));
    }
}
