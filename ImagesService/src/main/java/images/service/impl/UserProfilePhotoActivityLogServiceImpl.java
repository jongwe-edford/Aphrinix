package images.service.impl;

import images.model.UserProfileActivityLog;
import images.repository.UserProfilePhotoActivityLogRepository;
import images.service.UserProfilePhotoActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfilePhotoActivityLogServiceImpl implements UserProfilePhotoActivityLogService {
    private final UserProfilePhotoActivityLogRepository userProfilePhotoActivityLogRepository;
    @Override
    public void saveActivity(UserProfileActivityLog shopBannerActivityLog) {
        userProfilePhotoActivityLogRepository.save(shopBannerActivityLog);
    }
}
