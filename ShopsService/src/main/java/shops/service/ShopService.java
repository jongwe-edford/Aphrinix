package shops.service;

import shops.exception.EmailAlreadyExistException;
import shops.exception.ShopManagerAlreadyExistInShop;
import shops.model.Shop;
import shops.model.User;
import shops.payload.request.ShopRegistrationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShopService {
    Shop saveShop(ShopRegistrationRequest request, HttpServletRequest servletRequest,String token) throws EmailAlreadyExistException;
    Shop getShopByID(String id);
    Shop findByShopAdmin(String email);
    List<Shop> getAllShops();

    void sendShopManagerRegistrationLink(String email, String shopId);
    String  addShopManager(User user,String shopId) throws ShopManagerAlreadyExistInShop;
}
