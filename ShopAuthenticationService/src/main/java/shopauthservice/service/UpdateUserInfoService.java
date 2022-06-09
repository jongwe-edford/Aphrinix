package shopauthservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateUserInfoService {
    String updateUserProfilePhoto(String email, MultipartFile file);
}
