package shops.service;

import org.springframework.web.multipart.MultipartFile;
import shops.model.Shop;

public interface UpdateShopInfoService {
    String updateShopBanner(String email, MultipartFile file);

    void updateShopName(String shopId, String name);

    void updateShopEmail(String email);
}
