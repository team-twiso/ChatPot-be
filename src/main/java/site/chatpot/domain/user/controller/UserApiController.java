package site.chatpot.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chatpot.config.security.auth.UserDto;
import site.chatpot.domain.common.response.Api;
import site.chatpot.domain.user.controller.response.UserResponse;
import site.chatpot.domain.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping
    public Api<UserResponse> getUser(@AuthenticationPrincipal UserDto userDto) {
        UserResponse response = userService.getUser(userDto);
        return Api.successWithData(response);
    }
}
