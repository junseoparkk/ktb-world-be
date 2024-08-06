package com.singtanglab.ktbworld.service.place;

import com.singtanglab.ktbworld.dto.place.PlaceDto;
import java.util.List;

public interface PlaceService {
    List<PlaceDto> findPlaces(String section);
}
