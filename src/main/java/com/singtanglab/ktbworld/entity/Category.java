package com.singtanglab.ktbworld.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public enum Category {
    LAUNDRY("세탁"),
    TAXI("택시"),
    GONGGU("공구");

    private String value;

    Category(String value) {
        this.value = value;
    }
}
