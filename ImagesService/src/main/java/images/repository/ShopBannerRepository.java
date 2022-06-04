package images.repository;

import images.model.ShopBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ShopBannerRepository extends JpaRepository<ShopBanner,Long> {
    ShopBanner findByShopId(String shopId);
    @Transactional
    void deleteByShopId(String shopId);
    Boolean existsByShopId(String shopID);

}
