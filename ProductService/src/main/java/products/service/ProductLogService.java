package products.service;

import org.springframework.http.HttpRequest;
import products.model.ProductLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductLogService {
    void log(String productId, String userId, HttpServletRequest httpRequest);
    List<ProductLog> findAllLogsByProductId(String productId);
    List<ProductLog> findAllLogsByUserId(String userId);
}
