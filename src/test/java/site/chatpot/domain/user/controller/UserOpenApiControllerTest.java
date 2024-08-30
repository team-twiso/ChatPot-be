package site.chatpot.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.chatpot.utils.TestUtils.userRegisterRequest;
import static site.chatpot.utils.TestUtils.userRegisterResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import site.chatpot.config.security.SecurityConfig;
import site.chatpot.config.security.jwt.JwtAuthenticationFilter;
import site.chatpot.config.security.jwt.JwtUtils;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.service.UserService;

@WebMvcTest(controllers = UserOpenApiController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class, JwtUtils.class}))
@ExtendWith(MockitoExtension.class)
class UserOpenApiControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void signUpSuccess() throws Exception {
        //given
        UserRegisterRequest request = userRegisterRequest();
        UserRegisterResponse response = userRegisterResponse();
        when(userService.register(any(UserRegisterRequest.class))).thenReturn(response);
        //when
        ResultActions perform = mockMvc.perform(
                multipart("/open-api/users/register")
                        .file("profile", request.profile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("email", request.email())
                        .param("password", request.password())
                        .param("name", request.name())
                        .param("nickname", request.nickname())
                        .param("birthDate", request.birthDate())
                        .param("gender", request.gender().name())
                        .with(csrf())
        );
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id", response.id()).exists());
    }

    @Test
    @DisplayName("회원가입 실패 - 파라미터 누락")
    @WithMockUser
    void signUpFailInvalidParameter() throws Exception {
        //given
        UserRegisterRequest request = userRegisterRequest();
        //when
        ResultActions perform = mockMvc.perform(
                multipart("/open-api/users/register")
                        .file("profile", request.profile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("email", request.email())
                        .param("name", request.name())
                        .param("nickname", request.nickname())
                        .param("birthDate", request.birthDate())
                        .param("gender", request.gender().name())
                        .with(csrf())
        );
        //then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value("요청한 값이 올바르지 않습니다."))
                .andExpect(jsonPath("$.result[0].field").value("password"));
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 양식 불일치")
    @WithMockUser
    void signUpFailInvalidEmail() throws Exception {
        //given
        UserRegisterRequest request = userRegisterRequest();
        //when
        ResultActions perform = mockMvc.perform(
                multipart("/open-api/users/register")
                        .file("profile", request.profile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("email", "emailnaver.com")
                        .param("name", request.name())
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("birthDate", request.birthDate())
                        .param("gender", request.gender().name())
                        .with(csrf()));
        //then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.isSuccessful").value(false))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value("요청한 값이 올바르지 않습니다."))
                .andExpect(jsonPath("$.result[0].field").value("email"));

    }

}
