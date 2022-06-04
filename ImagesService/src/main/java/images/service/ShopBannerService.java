package images.service;

import images.model.ShopBanner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ShopBannerService {
    String saveBanner(String shopId, MultipartFile file, HttpServletRequest request) throws IOException;

    String getBannerUrl(String shopID,HttpServletRequest request);

    void deleteBannerID(String shopId,HttpServletRequest request);
    ResponseEntity<byte[]> getImageBytes(String shopId,HttpServletRequest request);
    ShopBanner getShopBanner(String shopId,HttpServletRequest request);

    void updateShopBanner(String shopId,MultipartFile file,HttpServletRequest request) throws IOException;
}
