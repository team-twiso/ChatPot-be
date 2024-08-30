package site.chatpot.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chatpot.domain.common.response.Api;
import site.chatpot.domain.user.controller.request.UserLoginRequest;
import site.chatpot.domain.user.controller.request.UserRegisterRequest;
import site.chatpot.domain.user.controller.response.UserLoginResponse;
import site.chatpot.domain.user.controller.response.UserRegisterResponse;
import site.chatpot.domain.user.service.UserService;

@RestController
@RequestMapping("/open-api/users")
@RequiredArgsConstructor
public class UserOpenApiController {

    private final UserService userService;

    @PostMapping("/register")
    public Api<UserRegisterResponse> register(@Valid @ModelAttribute UserRegisterRequest request) {
        UserRegisterResponse response = userService.register(request);
        return Api.successWithData(response);
    }

    @PostMapping("/login")
    public Api<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.login(request);
        return Api.successWithData(response);
    }
}
