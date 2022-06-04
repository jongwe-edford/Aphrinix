package shops.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shops.exception.EmailAlreadyExistException;
import shops.model.Shop;
import shops.payload.request.ShopRegistrationRequest;
import shops.service.ShopService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "shops")
@AllArgsConstructor
public class ShopsController {
    private final ShopService shopService;

    @PostMapping(path = "create")
    public ResponseEntity<Shop> createShop(@RequestBody ShopRegistrationRequest shopRegistrationRequest, HttpServletRequest request) throws EmailAlreadyExistException {
        return new ResponseEntity<>(shopService.saveShop(shopRegistrationRequest, request), HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public Shop getShop(@PathVariable("shopId") String shopId){
        return shopService.getShopByID(shopId);
    }
}
