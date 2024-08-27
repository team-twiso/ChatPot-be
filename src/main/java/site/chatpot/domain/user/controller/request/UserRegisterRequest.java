package site.chatpot.domain.user.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;
import site.chatpot.domain.user.entity.enums.Gender;

public record UserRegisterRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String nickname,
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "yyyy-mm-dd 형식으로 입력해 주세요.") String birthDate,
        @NotNull Gender gender,
        MultipartFile profile
) {

}
