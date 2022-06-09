package images.controller;

import images.service.ShopBannerService;
import images.service.UserProfilePhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = "images/profile")
@AllArgsConstructor
public class UserProfileImageController {
    private final UserProfilePhotoService userProfilePhotoService;

    @PostMapping(path = "save")
    public ResponseEntity<String> postImage(@RequestParam("email") String email, @RequestPart(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        return new ResponseEntity<>(userProfilePhotoService.savePhoto(email, file, request), HttpStatus.OK);
    }

    @GetMapping(path = "user")
    public ResponseEntity<String> getImageUrl(@RequestParam("email") String email, HttpServletRequest request) {
        return new ResponseEntity<>(userProfilePhotoService.getPhotoUrl(email, request), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<byte[]> getImage(@RequestParam("email") String email, HttpServletRequest request) {
        return userProfilePhotoService.getImageBytes(email, request);
    }
}
