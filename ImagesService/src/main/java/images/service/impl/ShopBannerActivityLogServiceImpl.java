package images.service.impl;

import images.model.ShopBannerActivityLog;
import images.repository.ShopBannerActivityLogRepository;
import images.service.ShopBannerActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopBannerActivityLogServiceImpl implements ShopBannerActivityLogService {
    private final ShopBannerActivityLogRepository activityLogRepository;

    @Override
    public void saveActivity(ShopBannerActivityLog shopBannerActivityLog) {
        activityLogRepository.save(shopBannerActivityLog);
    }
}
