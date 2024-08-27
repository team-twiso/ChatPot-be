package site.chatpot.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.image.service.ImageService;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.converter.UserConverter;
import site.chatpot.domain.user.entity.User;
import site.chatpot.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String PROFILE_IMAGE_PATH = "profile";
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserConverter userConverter;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        Image image = null;
        if (!request.profile().isEmpty()) {
            image = imageService.save(request.profile(), PROFILE_IMAGE_PATH);
        }
        User user = userConverter.toEntity(request, image);
        userRepository.save(user);
        return userConverter.toResponse(user);
    }
}
