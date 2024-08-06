package com.singtanglab.ktbworld.dto.user;

import com.singtanglab.ktbworld.entity.User;
import lombok.Builder;

@Builder
public record UserInfoDto(Long id, String nickname) {
    public static UserInfoDto mapToDto(User user){
        return UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
