package products.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import products.model.ProductLog;
import products.repository.ProductLogRepository;
import products.service.ProductLogService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductLogServiceImpl implements ProductLogService {
    private final ProductLogRepository logRepository;

    @Override
    public void log(String productId, String userId, HttpServletRequest httpRequest) {
        ProductLog productLog = ProductLog
                .builder()
                .viewedAt(LocalDateTime.now())
                .ipAddress(httpRequest.getRemoteAddr())
                .userId(userId)
                .productId(productId)
                .build();
        logRepository.save(productLog);
    }

    @Override
    public List<ProductLog> findAllLogsByProductId(String productId) {
        return logRepository.findAllByProductId(productId);
    }

    @Override
    public List<ProductLog> findAllLogsByUserId(String userId) {
        return logRepository.findAllByUserId(userId);
    }
}
