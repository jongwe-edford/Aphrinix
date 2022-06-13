package images.service.profile_picture.impl;

import images.model.UserProfileActivityLog;
import images.repository.UserProfilePhotoActivityLogRepository;
import images.service.profile_picture.UserProfilePhotoActivityLogService;
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
