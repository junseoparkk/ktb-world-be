package com.singtanglab.ktbworld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "places")
public class PublicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_id;

    private String title;
    private String address;
    private String tag;
    private String thumbnail_url;
}
