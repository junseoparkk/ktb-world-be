package com.singtanglab.ktbworld.service.place;

import com.singtanglab.ktbworld.controller.TestController;
import com.singtanglab.ktbworld.dto.place.PlaceDto;
import com.singtanglab.ktbworld.entity.PublicData;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final TestController testController;

    @Override
    public List<PlaceDto> findPlaces(String section) {
        log.info("PlaceService findPlaces");
        Map<String, Object> apiDataList = testController.getApiMapData();
        log.info("PlaceService apiDataList : {}", apiDataList);

        // 필터링에 사용할 단어 리스트
        List<String> filterKeywords;

        // section
        // 0: 전체, 1: 제주시, 2: 조천,구좌, 3: 성산,표선, 4: 서귀포,남원,중문, 5: 안덕,대정, 6: 한경,한림,애월
        if(section.equals("0")) {
            filterKeywords = List.of("제주특별자치도");
        }else if(section.equals("1")) {
            filterKeywords = List.of("제주시");
        } else if(section.equals("2")) {
            filterKeywords = List.of("구좌", "조천");
        } else if(section.equals("3")) {
            filterKeywords = List.of("성산", "표선");
        } else if(section.equals("4")) {
            filterKeywords = List.of("서귀포", "남원", "중문");
        } else if(section.equals("5")) {
            filterKeywords = List.of("안덕", "대정");
        } else if(section.equals("6")) {
            filterKeywords = List.of("한경", "한림", "애월");
        } else {
            filterKeywords = List.of();
        }

        List<PublicData> publicDataList = apiDataList.values().stream()
                .filter(value -> value instanceof PublicData)
                .map(value -> (PublicData) value)
                .filter(data -> data.getAddress() != null && filterKeywords.stream().anyMatch(keyword -> data.getAddress().contains(keyword)))
                .collect(Collectors.toList());

        return publicDataList.stream()
                .map(publicData -> {
                    PlaceDto placeDto = new PlaceDto();
                    placeDto.setId(publicData.getPlace_id());
                    placeDto.setTitle(publicData.getTitle());
                    placeDto.setAddress(publicData.getAddress());
                    placeDto.setTag(publicData.getTag());
                    placeDto.setThumbnail_url(publicData.getThumbnail_url());
                    placeDto.setSection(section);
                    return placeDto;
                })
                .collect(Collectors.toList());
    }
}