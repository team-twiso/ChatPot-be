package site.chatpot.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static site.chatpot.utils.TestUtils.image;
import static site.chatpot.utils.TestUtils.user;
import static site.chatpot.utils.TestUtils.userRegisterRequest;
import static site.chatpot.utils.TestUtils.userRegisterRequestNotImage;
import static site.chatpot.utils.TestUtils.userRegisterResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.image.service.ImageService;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.converter.UserConverter;
import site.chatpot.domain.user.entity.User;
import site.chatpot.domain.user.exception.EmailAlreadyExistsException;
import site.chatpot.domain.user.exception.NicknameAlreadyExistsException;
import site.chatpot.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ImageService imageService;

    @Test
    @DisplayName("회원 가입 - 이미지 O")
    void register() {
        // given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserRegisterRequest request = userRegisterRequest();
        String encodedPassword = encoder.encode(request.password());
        when(imageService.save(any(MultipartFile.class), any(String.class))).thenReturn(image());
        when(userConverter.toEntity(any(UserRegisterRequest.class), any(Image.class), any(String.class)))
                .thenReturn(user(request, encodedPassword));
        when(userConverter.toResponse(any(User.class))).thenReturn(userRegisterResponse());
        when(userRepository.save(any(User.class))).thenReturn(user(request, encodedPassword));
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(userRepository.existsByNickname(any(String.class))).thenReturn(false);
        // when
        UserRegisterResponse response = userService.register(request);
        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(encoder.matches(request.password(), encodedPassword)).isTrue();
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByEmail(any(String.class));
        verify(userRepository, times(1)).existsByNickname(any(String.class));
        verify(imageService, times(1)).save(any(MultipartFile.class), any(String.class));
        verify(userConverter, times(1)).toEntity(any(UserRegisterRequest.class), any(Image.class), any(String.class));
        verify(userConverter, times(1)).toResponse(any(User.class));
        verify(passwordEncoder, times(1)).encode(request.password());
    }

    @Test
    @DisplayName("회원 가입 - 이미지 X")
    void registerNotImage() {
        // given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserRegisterRequest request = userRegisterRequestNotImage();
        String encodedPassword = encoder.encode(request.password());
        when(userConverter.toEntity(any(UserRegisterRequest.class), eq(null), any(String.class)))
                .thenReturn(user(request, encodedPassword));
        when(userConverter.toResponse(any(User.class))).thenReturn(userRegisterResponse());
        when(userRepository.save(any(User.class))).thenReturn(user(request, encodedPassword));
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(userRepository.existsByNickname(any(String.class))).thenReturn(false);
        // when
        UserRegisterResponse response = userService.register(request);
        // then
        assertThat(response.id()).isEqualTo(1L);
        verify(imageService, times(0)).save(any(MultipartFile.class), any(String.class));
    }

    @Test
    @DisplayName("회원 가입 - 이미 존재하는 닉네임")
    void register_exist_nickname() throws Exception {
        //given
        UserRegisterRequest request = userRegisterRequestNotImage();
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(userRepository.existsByNickname(any(String.class))).thenReturn(true);
        //when & then
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(NicknameAlreadyExistsException.class)
                .hasMessage("이미 사용중인 닉네임입니다.");
    }

    @Test
    @DisplayName("회원 가입 - 이미 존재하는 이메일")
    void register_exist_email() throws Exception {
        //given
        UserRegisterRequest request = userRegisterRequestNotImage();
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
        //when & then
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }
}
