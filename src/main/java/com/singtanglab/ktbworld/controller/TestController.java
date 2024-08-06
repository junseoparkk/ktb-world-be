package com.singtanglab.ktbworld.controller;

import com.singtanglab.ktbworld.entity.PublicData;
import com.singtanglab.ktbworld.repository.PublicDataRepository;
import com.singtanglab.ktbworld.response.PublicDataResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private PublicDataRepository publicDataRepository;

    @GetMapping("/fetch-data")
    public String fetchData() {
        String apiUrl = "https://api.visitjeju.net/api/contents/list?_siteId=jejuavj&locale=kr&device=pc&cate1cd=cate0000000002&tag=&sorting=reviewcnt+desc,+title_kr+asc&region1cd=&region2cd=&pageSize=500&page=1";
        RestTemplate restTemplate = new RestTemplate();
        PublicDataResponse response = restTemplate.getForObject(apiUrl, PublicDataResponse.class);

        List<PublicData> dataList = response.getData();
        publicDataRepository.saveAll(dataList);

        return "Data fetched and saved successfully!";
    }
}
