package images.repository;

import images.model.ShopBannerActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopBannerActivityLogRepository extends JpaRepository<ShopBannerActivityLog, Long> {
    List<ShopBannerActivityLog > findAllByShopId(String shopId);
}