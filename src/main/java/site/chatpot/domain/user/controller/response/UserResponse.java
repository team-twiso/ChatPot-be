package site.chatpot.domain.user.controller.response;

public record UserResponse(
        Long id,
        String email,
        String name,
        String nickname,
        String birthDate,
        String gender,
        String profile,
        String createdAt,
        String lastModifiedAt
) {
}
