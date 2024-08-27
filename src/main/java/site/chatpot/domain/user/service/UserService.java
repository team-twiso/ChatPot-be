package site.chatpot.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.image.service.ImageService;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.converter.UserConverter;
import site.chatpot.domain.user.entity.User;
import site.chatpot.domain.user.exception.EmailAlreadyExistsException;
import site.chatpot.domain.user.exception.NicknameAlreadyExistsException;
import site.chatpot.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String PROFILE_IMAGE_PATH = "profile";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserConverter userConverter;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        checkEmailDuplication(request.email());
        checkNicknameDuplication(request.nickname());
        Image image = null;
        if (request.profile() != null) {
            image = imageService.save(request.profile(), PROFILE_IMAGE_PATH);
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = userConverter.toEntity(request, image, encodedPassword);
        userRepository.save(user);
        return userConverter.toResponse(user);
    }

    private void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }

    private void checkNicknameDuplication(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistsException();
        }
    }
}
