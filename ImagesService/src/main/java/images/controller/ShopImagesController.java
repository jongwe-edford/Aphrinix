package images.controller;


import images.service.shop_banner.ShopBannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = "images/shop-banner")
@AllArgsConstructor
public class ShopImagesController {
    private final ShopBannerService shopBannerService;

    @PostMapping(path = "create/{shopId}")
    public ResponseEntity<String> postImage(@PathVariable("shopId") String shopId, @RequestPart(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        return new ResponseEntity<>( shopBannerService.saveBanner(shopId, file, request),HttpStatus.OK);
    }

    @GetMapping(path = "shop/{shopId}")
    public ResponseEntity<String> getImageUrl(@PathVariable("shopId") String shopId, HttpServletRequest request) {
        return new ResponseEntity<>(shopBannerService.getBannerUrl(shopId, request), HttpStatus.OK);
    }

    @GetMapping(path = "{shopId}")
    public ResponseEntity<byte[]> getImage(@PathVariable("shopId") String shopId,HttpServletRequest request) {
        return shopBannerService.getImageBytes(shopId,request);
    }


}
