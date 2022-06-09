package images.service;

import images.model.ShopBanner;
import images.model.UserProfileImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UserProfilePhotoService {
    String savePhoto(String shopId, MultipartFile file, HttpServletRequest request) throws IOException;

    String getPhotoUrl(String shopID,HttpServletRequest request);

    void deletePhoto(String email,HttpServletRequest request);
    ResponseEntity<byte[]> getImageBytes(String shopId,HttpServletRequest request);
    UserProfileImage getPhoto(String shopId, HttpServletRequest request);

    void updatePhoto(String shopId,MultipartFile file,HttpServletRequest request) throws IOException;
}
