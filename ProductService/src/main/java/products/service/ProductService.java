package products.service;

import org.springframework.web.multipart.MultipartFile;
import products.exception.ReviewNotFoundException;
import products.model.Product;
import products.model.Review;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    Product saveProduct(Product product, MultipartFile[] multipartFiles);
    Product findProductById(String id, HttpServletRequest request);
    String reviewProduct(String productId,String authorizationHeader,String message);
    List<Product> allProducts(HttpServletRequest request);
    List<Product> allProductsByShopId(String shopId,HttpServletRequest request);

}
