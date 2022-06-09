package shops.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shops.exception.EmailAlreadyExistException;
import shops.exception.ShopManagerAlreadyExistInShop;
import shops.model.Shop;
import shops.model.User;
import shops.payload.request.ShopRegistrationRequest;
import shops.service.ShopService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "shops")
@AllArgsConstructor
public class ShopsController {
    private final ShopService shopService;

    @PostMapping(path = "create")
    public ResponseEntity<Shop> createShop(@RequestBody ShopRegistrationRequest shopRegistrationRequest, HttpServletRequest request, @RequestHeader("Authorization") String authorizationToken) throws EmailAlreadyExistException {
        System.out.println("Authorization header::" + authorizationToken);
        return new ResponseEntity<>(shopService.saveShop(shopRegistrationRequest, request, authorizationToken), HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public Shop getShop(@PathVariable("shopId") String shopId) {
        return shopService.getShopByID(shopId);
    }

    @GetMapping("admin/{token}")
    public Shop getShopByAdmin(@PathVariable("token") String email) {
        return shopService.findByShopAdmin(email);
    }

    @GetMapping(path = "manager")
    public ResponseEntity<Void> sendShopManagerRegistrationLink(@RequestParam("email") String email, @RequestParam("id") String shopId) {
        shopService.sendShopManagerRegistrationLink(email, shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "managers/add/{shopId}")
    public ResponseEntity<?> addShopManager(@PathVariable("shopId") String shopId, @RequestBody User user) throws ShopManagerAlreadyExistInShop {
        return ResponseEntity.ok(shopService.addShopManager(user, shopId));
    }


}
