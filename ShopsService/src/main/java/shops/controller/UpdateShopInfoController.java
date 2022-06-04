package shops.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shops.service.UpdateShopInfoService;

@RestController
@RequestMapping(path = "shop/update")
@AllArgsConstructor
public class UpdateShopInfoController {
    private final UpdateShopInfoService updateShopInfoService;

    @PostMapping(path = "{shopId}")
    public ResponseEntity<String> updateBanner(@PathVariable(value = "shopId") String shopId, @RequestPart(value = "file") MultipartFile multipartFile) {
        return ResponseEntity.ok(updateShopInfoService.updateShopBanner(shopId, multipartFile));
    }

    @PatchMapping(path = "{shopId}")
    public ResponseEntity<Void> updateName(@PathVariable(value = "shopId") String shopId,@RequestParam(value = "name") String name) {
        updateShopInfoService.updateShopName(shopId, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
