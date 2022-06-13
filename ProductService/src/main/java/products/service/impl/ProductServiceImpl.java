package products.service.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import products.exception.ReviewNotFoundException;
import products.model.Product;
import products.model.Review;
import products.repository.ProductRepository;
import products.service.ProductLogService;
import products.service.ProductService;
import products.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ReviewService reviewService;
    private final ProductLogService productLogService;

    @Override
    public Product saveProduct(Product product, MultipartFile[] multipartFiles) {
        Product before = productRepository.save(product);
        String getUrl = "http://IMAGES-SERVICE/images/products/{id}";
        String postUrl = "http://IMAGES-SERVICE/images/products/c/" + before.getId();
        // multipart form body
        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        for (MultipartFile file : multipartFiles) {
            Resource resource = file.getResource();
            parts.add("files", resource);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);
        restTemplate.postForEntity(postUrl, httpEntity, String.class);
        ResponseEntity<String[]> images = restTemplate.getForEntity(getUrl, String[].class, before.getId());
        before.setImages(Arrays.asList(Objects.requireNonNull(images.getBody())));
        return productRepository.save(before);

    }

    @Override
    public Product findProductById(String id, HttpServletRequest request) {
        productLogService.log(id, "12", request);
        return productRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public String reviewProduct(String productId, String authorizationHeader, String message) {

        return "Reviewed successfully";
    }

    @Override
    public List<Product> allProducts(HttpServletRequest request) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            productLogService.log(product.getId(), "12", request);
        }
        return products;
    }

    @Override
    public List<Product> allProductsByShopId(String shopId, HttpServletRequest request) {
        List<Product> products = productRepository.findAllByShopId(shopId);
        for (Product product : products) {
            productLogService.log(product.getId(), "12", request);
        }
        return products;
    }


}
