package products.controller.products;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import products.exception.ReviewNotFoundException;
import products.model.Product;
import products.model.Review;
import products.service.ProductLogService;
import products.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping(path = "products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ProductLogService productLogService;

    @Override
    @PostMapping(path = "create")
    public ResponseEntity<Product> createProduct(
            @RequestPart(value = "product") Product product,
            @RequestPart(value = "images") MultipartFile[] multipartFiles) {
        return new ResponseEntity<>(productService.saveProduct(product, multipartFiles), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "d/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "id") String id, HttpServletRequest request) {
        return ResponseEntity.ok(productService.findProductById(id, request));
    }

    @Override
    @PatchMapping(path = "review/{id}")
    public ResponseEntity<String> reviewProduct(
            @PathVariable("id") String productId,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody String message
    ) {
        return ResponseEntity.ok(productService.reviewProduct(productId, authorizationHeader, message));
    }

    @Override
    @GetMapping(path = "all")
    public ResponseEntity<List<Product>> findAllProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productService.allProducts(request));
    }

    @Override
    @GetMapping(path = "all/{productId}")
    public ResponseEntity<List<Product>> findAllProductsByShopId(@PathVariable(value = "productId") String shopId, HttpServletRequest request) {
        return ResponseEntity.ok(productService.allProductsByShopId(shopId, request));
    }

}
