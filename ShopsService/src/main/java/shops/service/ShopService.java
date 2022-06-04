package shops.service;

import shops.exception.EmailAlreadyExistException;
import shops.model.Shop;
import shops.payload.request.ShopRegistrationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShopService {
    Shop saveShop(ShopRegistrationRequest request, HttpServletRequest servletRequest) throws EmailAlreadyExistException;
    Shop getShopByID(String id);
    Shop getShopByEmail(String email);
    List<Shop> getAllShops();
}
