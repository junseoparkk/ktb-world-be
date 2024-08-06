package com.singtanglab.ktbworld.dto.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceDto {
    private Long id;
    private String title;
    private String address;
    private String tag;
    private String thumbnail_url;
    private String section;
}