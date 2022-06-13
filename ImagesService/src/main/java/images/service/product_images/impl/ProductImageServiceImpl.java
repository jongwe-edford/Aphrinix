package images.service.product_images.impl;

import images.model.ProductImage;
import images.model.UserProfileActivityLog;
import images.model.UserProfileImage;
import images.model.enums.ActivityType;
import images.repository.ProductImageRepository;
import images.service.product_images.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;

    @Override
    public String saveImages(String productId, MultipartFile[] multipartFiles) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            ProductImage productImage = ProductImage
                    .builder()
                    .productId(productId)
                    .imageBytes(file.getBytes())
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .postedOn(LocalDateTime.now())
                    .build();
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        return "Images saved successfully";
    }

    @Override
    public List<String> productImages(String productId) {
        List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            String image = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/products/i/" + productImage.getId())
                    .toUriString();
            images.add(image);
        }
        return images;
    }

    @Override
    public void deleteAllByProductId(String productId) {
        productImageRepository.deleteAllByProductId(productId);
    }


    @Override
    public ResponseEntity<byte[]> getImageBytes(long productId) {
        ProductImage userProfileImage = getPhoto(productId);
        byte[] imageBytes = userProfileImage.getImageBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userProfileImage.getName() + "\"")
                .contentType(MediaType.parseMediaType(userProfileImage.getType()))
                .body(imageBytes);
    }

    @Override
    public ProductImage getPhoto(long productId) {
        return productImageRepository.findById(productId).orElseThrow();
    }

}
