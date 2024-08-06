package com.singtanglab.ktbworld.controller;

import com.singtanglab.ktbworld.dto.place.PlaceDto;
import com.singtanglab.ktbworld.service.place.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/{section}")
    public ResponseEntity<List<PlaceDto>> getPlace(@PathVariable("section") String section) {
        try {
            log.info("PlaceController");
            List<PlaceDto> places = placeService.findPlaces(section);
            log.info("PlaceController 서비스 결과값 : {}", places);
            return ResponseEntity.ok(places);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
