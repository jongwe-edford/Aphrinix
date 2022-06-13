package images.service.product_images;

import images.model.ProductImage;
import images.model.UserProfileImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    String saveImages(String productId, MultipartFile[] multipartFiles) throws IOException;

    List<String> productImages(String productId);

    void deleteAllByProductId(String productId);

    ResponseEntity<byte[]> getImageBytes(long productId);

    ProductImage getPhoto(long productId);

}
