package shopauthservice.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shopauthservice.models.User;
import shopauthservice.service.UpdateUserInfoService;

@Service
@AllArgsConstructor
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {
    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    @Override
    public String updateUserProfilePhoto(String email, MultipartFile file) {
        Query query = new Query(Criteria.where("email").is(email));
        String postUrl = "http://IMAGES-SERVICE/images/profile/save?email={email}" ;
        String getUrl = "http://IMAGES-SERVICE/images/profile/user?email="+email;
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

        restTemplate.postForEntity(postUrl, httpEntity, String.class,email);
        String imageUrl = restTemplate.getForObject(getUrl, String.class);
        System.out.print("Image url:::===> %s \n"+imageUrl);
        update.set("photoUrl", imageUrl);
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        System.out.println(result.getModifiedCount() + " document(s) updated..");
        return "Image uploaded successfully";
    }
}
