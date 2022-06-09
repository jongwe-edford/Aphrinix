package images.service.impl;

import images.model.ShopBanner;
import images.model.ShopBannerActivityLog;
import images.model.enums.ActivityType;
import images.repository.ShopBannerRepository;
import images.service.ShopBannerActivityLogService;
import images.service.ShopBannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@AllArgsConstructor
@Transactional
public class ShopBannerServiceImpl implements ShopBannerService {
    private final ShopBannerRepository shopBannerRepository;
    private final ShopBannerActivityLogService shopBannerActivityLogService;

    @Override
    public String saveBanner(String shopId, MultipartFile file, HttpServletRequest request) throws IOException {
        if (shopBannerRepository.existsByShopId(shopId))
            deleteBannerID(shopId, request);

            ShopBanner shopBanner = ShopBanner
                    .builder()
                    .shopId(shopId)
                    .imageBytes(file.getBytes())
                    .name(file.getOriginalFilename())
                    .postedOn(LocalDateTime.now())
                    .postingIpAddress(request.getRemoteHost())
                    .type(file.getContentType())
                    .build();
            shopBannerRepository.save(shopBanner);
            ShopBannerActivityLog shopBannerActivityLog =
                    ShopBannerActivityLog
                            .builder()
                            .shopId(shopId)
                            .activityType(ActivityType.UPLOAD)
                            .timestamp(Timestamp.from(Instant.now()))
                            .ipAddress(request.getRemoteAddr())
                            .build();
            shopBannerActivityLogService.saveActivity(shopBannerActivityLog);

            return "Photo uploaded successfully";

    }

    @Override
    public String getBannerUrl(String shopID, HttpServletRequest servletRequest) {
        ShopBanner shopBanner = shopBannerRepository.findByShopId(shopID);
        ShopBannerActivityLog shopBannerActivityLog =
                ShopBannerActivityLog
                        .builder()
                        .shopId(shopID)
                        .activityType(ActivityType.READ)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(servletRequest.getRemoteAddr())
                        .build();
        shopBannerActivityLogService.saveActivity(shopBannerActivityLog);
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/shop-banner/")
                .path(String.valueOf(shopBanner.getShopId()))
                .toUriString();
    }

    @Override
    public void deleteBannerID(String shopId, HttpServletRequest request) {
        shopBannerRepository.deleteByShopId(shopId);
        ShopBannerActivityLog shopBannerActivityLog =
                ShopBannerActivityLog
                        .builder()
                        .shopId(shopId)
                        .activityType(ActivityType.DELETE)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        shopBannerActivityLogService.saveActivity(shopBannerActivityLog);
    }

    @Override
    public ResponseEntity<byte[]> getImageBytes(String shopId,HttpServletRequest request) {
        ShopBanner shopBanner = getShopBanner(shopId,request);
        byte[] imageBytes = shopBanner.getImageBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + shopBanner.getName() + "\"")
                .contentType(MediaType.parseMediaType(shopBanner.getType()))
                .body(imageBytes);
    }

    @Override
    public ShopBanner getShopBanner(String shopId,HttpServletRequest request) {
        ShopBanner shopBanner = shopBannerRepository.findByShopId(shopId);
        ShopBannerActivityLog shopBannerActivityLog =
                ShopBannerActivityLog
                        .builder()
                        .shopId(shopId)
                        .activityType(ActivityType.READ)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        shopBannerActivityLogService.saveActivity(shopBannerActivityLog);
        return shopBanner;
    }

    @Override
    public void updateShopBanner(String shopId, MultipartFile file, HttpServletRequest request) throws IOException {
        deleteBannerID(shopId, request);
        saveBanner(shopId, file, request);
    }
}
