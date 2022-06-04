package shops.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shops.model.Shop;
import shops.service.UpdateShopInfoService;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UpdateShopInfoServiceImpl implements UpdateShopInfoService {
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;

    @Override
    public String updateShopBanner(String shopId, MultipartFile file) {
        Query query = new Query(Criteria.where("shopId").is(shopId));
        String postUrl = "http://IMAGES-SERVICE/images/shop-banner/create/" + shopId;
        String getUrl = "http://IMAGES-SERVICE/images/shop-banner/shop/" + shopId;

        Update update = new Update();

        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // multipart form body
        Resource invoicesResource = file.getResource();

        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", invoicesResource);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);

        restTemplate.postForEntity(postUrl, httpEntity, String.class);
            String imageUrl = restTemplate.getForObject(getUrl, String.class);
            update.set("shopBanner", imageUrl);
            UpdateResult result = mongoTemplate.updateFirst(query, update, Shop.class);
            System.out.println(result.getModifiedCount() + " document(s) updated..");
        return "Image uploaded successfully";
    }

    @Override
    public void updateShopName(String shopId, String name) {
        Query query = new Query(Criteria.where("shopId").is(shopId));
        Update update = new Update();
        update.set("name", name);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Shop.class);
        System.out.println(result.getModifiedCount() + " document(s) updated..");
    }

    @Override
    public void updateShopEmail(String email) {

    }
}
