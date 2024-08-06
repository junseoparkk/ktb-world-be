package com.singtanglab.ktbworld.dto.user;

import java.util.List;

public record SearchResponse(String message, SearchData data) {
    public record SearchData(int result_count, List<UserInfoDto> users) {}
}