package com.singtanglab.ktbworld.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record PublicDataResponse (
    @JsonProperty("items")
    List<PlaceDto> items
){
    public record PlaceDto (
            @JsonProperty("title")
            String title,

            @JsonProperty("roadaddress")
            String address,

            @JsonProperty("alltag")
            String tag,

            @JsonProperty("repPhoto")
            Map<String, Object> repPhoto
    ) {
        public record Photoid (
                @JsonProperty("photoid")
                Map<String, Thumbnailpath> photoid
        ) {
            public record Thumbnailpath (
                    @JsonProperty("thumbnailpath")
                    String thumbnailUrl
            ) { }
        }
    }

}
