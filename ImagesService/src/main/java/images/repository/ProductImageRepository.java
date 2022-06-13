package images.repository;

import images.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

     List<ProductImage> findAllByProductId(String productId) ;


     void  deleteAllByProductId(String productId);
}
