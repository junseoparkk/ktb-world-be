package com.singtanglab.ktbworld.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singtanglab.ktbworld.entity.PublicData;
import com.singtanglab.ktbworld.repository.PublicDataRepository;
import com.singtanglab.ktbworld.response.PublicDataResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@Slf4j
@Getter
public class TestController {
    List<PublicData> apiDataList; // 야매버전 용
    Map<String, Object> apiMapData;
    @Autowired
    private PublicDataRepository publicDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GetMapping("/fetch-data")
    public ResponseEntity<String> fetchData() {
        String apiUrl = "http://api.visitjeju.net/vsjApi/contents/searchList?apiKey=7886e5a411224170a6f707797cf62829&locale=kr&category=c1&page=1";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer your_token_here"); // 필요한 경우
        headers.set("User-Agent", "YourAppName/1.0"); // 필요한 경우

        // 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // API 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        log.info("response : {}", response);
        String jsonResponse = response.getBody();
        log.info("jsonResponse : {}", jsonResponse);

        // 야매버전
        try {
            Map<String, Object> jsonDataMap = objectMapper.readValue(jsonResponse, Map.class);
            // 출력하여 확인
            System.out.println("jsonDataMap: " + jsonDataMap);
            apiMapData = jsonDataMap;
            System.out.println("apiMapData: " + apiMapData);
            log.info("apiMapData : {}", apiMapData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DB 시도 버전
        try {
            // JSON 문자열을 PublicDataResponse 객체 리스트로 변환
            PublicDataResponse publicDataResponse = objectMapper.readValue(jsonResponse, PublicDataResponse.class);
            log.info("publicDataResponse : {}", publicDataResponse);

            // 필터링된 데이터 저장
            List<PublicData> publicDataList = Collections.emptyList();
            if(publicDataResponse.items() != null) {
                publicDataList = publicDataResponse.items().stream()
                        .map(data -> {
                            PublicData publicData = new PublicData();
                            publicData.setTitle(data.title());
                            publicData.setAddress(data.address());
                            publicData.setTag(data.tag());
                            // thumbnailUrl 설정
                            String thumbnailUrl = null;
                            if (data.repPhoto() != null) {
                                for (Object photoid : data.repPhoto().values()) {
                                    if (photoid instanceof Map) {
                                        Map<String, Object> photoidMap = (Map<String, Object>) photoid;
                                        for (Object thumbnailpath : photoidMap.values()) {
                                            if (thumbnailpath instanceof Map) {
                                                Map<String, String> thumbnailpathMap = (Map<String, String>) thumbnailpath;
                                                thumbnailUrl = thumbnailpathMap.get("thumbnailpath");
                                                break;
                                            }
                                        }
                                    }
                                    if (thumbnailUrl != null) {
                                        break;
                                    }
                                }
                            }
                            publicData.setThumbnail_url(thumbnailUrl);
                            // 필요한 다른 필드들도 설정
                            return publicData;
                        })
                        .collect(Collectors.toList());
            }
            log.info("Filtered Data[0]: {}", publicDataList.get(0).toString());

            // 야매버전 용
            apiDataList = publicDataList;
            log.info("apiData : ", apiDataList);
//            publicDataRepository.saveAll(publicDataList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data processing failed.");
        }

        // 반환값 설정
        return ResponseEntity.ok("공공데이터 api 데이터 load 및 가공 성공");
    }
}
