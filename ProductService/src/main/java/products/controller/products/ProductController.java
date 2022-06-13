package products.controller.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import products.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductController {

    ResponseEntity<Product> createProduct(Product product, MultipartFile[] multipartFiles);

    ResponseEntity<Product> findProductById(String id, HttpServletRequest request);

    ResponseEntity<String> reviewProduct(String productId, String authorizationHeader, String message);

    ResponseEntity<List<Product>> findAllProducts(HttpServletRequest request);

    ResponseEntity<List<Product>> findAllProductsByShopId(String shopId, HttpServletRequest request);

}
