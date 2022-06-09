package shopauthservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopauthservice.service.UpdateUserInfoService;

@RestController
@RequestMapping(path = "shop/auth/update")
@AllArgsConstructor
public class UpdateUserInfoController {
    private final UpdateUserInfoService updateUserInfoService;

    @PostMapping()
    public ResponseEntity<String> updateBanner(@RequestParam(value = "email") String email, @RequestPart(value = "file") MultipartFile multipartFile) {
        return ResponseEntity.ok(updateUserInfoService.updateUserProfilePhoto(email, multipartFile));
    }


}
