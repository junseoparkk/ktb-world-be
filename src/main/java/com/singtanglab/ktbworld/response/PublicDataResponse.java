package com.singtanglab.ktbworld.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.singtanglab.ktbworld.entity.PublicData;
import java.util.List;

public class PublicDataResponse {
    @JsonProperty("data")
    private List<PublicData> data;

    public List<PublicData> getData() {
        return data;
    }
}
