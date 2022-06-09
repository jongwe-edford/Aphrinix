package images.service.impl;

import images.model.UserProfileActivityLog;
import images.model.UserProfileImage;
import images.model.enums.ActivityType;
import images.repository.UserProfilePhotoRepository;
import images.service.UserProfilePhotoActivityLogService;
import images.service.UserProfilePhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserProfilePhotoServiceImpl implements UserProfilePhotoService {
    private final UserProfilePhotoRepository userProfilePhotoRepository;
    private final UserProfilePhotoActivityLogService userProfilePhotoActivityLogService;

    @Override
    public String savePhoto(String email, MultipartFile file, HttpServletRequest request) throws IOException {
        if (userProfilePhotoRepository.existsByEmail(email))
            deletePhoto(email, request);

        UserProfileImage userProfileImage = UserProfileImage
                .builder()
                .email(email)
                .imageBytes(file.getBytes())
                .name(file.getOriginalFilename())
                .postedOn(LocalDateTime.now())
                .postingIpAddress(request.getRemoteHost())
                .type(file.getContentType())
                .build();
        userProfilePhotoRepository.save(userProfileImage);
        UserProfileActivityLog userProfileActivityLog =
                UserProfileActivityLog
                        .builder()
                        .email(email)
                        .activityType(ActivityType.UPLOAD)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        userProfilePhotoActivityLogService.saveActivity(userProfileActivityLog);

        return "Photo uploaded successfully";
    }

    @Override
    public String getPhotoUrl(String email, HttpServletRequest request) {
        UserProfileImage userProfileImage = userProfilePhotoRepository.findByEmail(email);
        UserProfileActivityLog userProfileActivityLog =
                UserProfileActivityLog
                        .builder()
                        .email(email)
                        .activityType(ActivityType.READ)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        userProfilePhotoActivityLogService.saveActivity(userProfileActivityLog);
        Map<String ,Object> variables=new HashMap<>();
        variables.put("email",email);
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/profile")
                .queryParam("email",email)
                .toUriString();
    }

    @Override
    public void deletePhoto(String email, HttpServletRequest request) {
        userProfilePhotoRepository.deleteByEmail(email);
        UserProfileActivityLog userProfileActivityLog =
                UserProfileActivityLog
                        .builder()
                        .email(email)
                        .activityType(ActivityType.DELETE)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        userProfilePhotoActivityLogService.saveActivity(userProfileActivityLog);
    }

    @Override
    public ResponseEntity<byte[]> getImageBytes(String shopId, HttpServletRequest request) {
        UserProfileImage userProfileImage = getPhoto(shopId, request);
        byte[] imageBytes = userProfileImage.getImageBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userProfileImage.getName() + "\"")
                .contentType(MediaType.parseMediaType(userProfileImage.getType()))
                .body(imageBytes);
    }

    @Override
    public UserProfileImage getPhoto(String email, HttpServletRequest request) {
        UserProfileImage shopBanner = userProfilePhotoRepository.findByEmail(email);
        UserProfileActivityLog userProfileActivityLog =
                UserProfileActivityLog
                        .builder()
                        .email(email)
                        .activityType(ActivityType.DELETE)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        userProfilePhotoActivityLogService.saveActivity(userProfileActivityLog);
        return shopBanner;
    }

    @Override
    public void updatePhoto(String shopId, MultipartFile file, HttpServletRequest request) throws IOException {
        deletePhoto(shopId, request);
        savePhoto(shopId, file, request);
    }
}
