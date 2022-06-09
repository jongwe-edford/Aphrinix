package images.service;

import images.model.ShopBannerActivityLog;

import java.util.List;

public interface ShopBannerActivityLogService {
    void saveActivity(ShopBannerActivityLog shopBannerActivityLog);
    List<ShopBannerActivityLog> activitiesList(String shopId);
}
