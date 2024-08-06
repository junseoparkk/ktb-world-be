package com.singtanglab.ktbworld.controller;

import com.singtanglab.ktbworld.dto.user.LoginRequest;
import com.singtanglab.ktbworld.dto.user.LoginResponse;
import com.singtanglab.ktbworld.dto.user.SearchResponse;
import com.singtanglab.ktbworld.dto.user.UserInfoDto;
import com.singtanglab.ktbworld.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            UserInfoDto userInfoDto = userService.login(request);
            LoginResponse response = new LoginResponse("LOGINED_SUCCESS", userInfoDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
        try {
            List<UserInfoDto> users = userService.findAllUsers();  // 모든 유저 조회
            return ResponseEntity.ok(users);  // 유저 리스트 반환
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> searchUsers(@RequestParam("nickname") String nickname) {
        try {
            List<UserInfoDto> users = userService.findUsersByNicknameContaining(nickname);  // 닉네임에 특정 문자가 포함된 유저 조회
            SearchResponse.SearchData searchData = new SearchResponse.SearchData(users.size(), users);
            SearchResponse response = new SearchResponse("USER_LIST_LOADED_SUCCESS", searchData);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
