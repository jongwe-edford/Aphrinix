package shops.service;

import shops.exception.ShopManagerAlreadyExistInShop;
import shops.model.ShopManager;

import java.util.List;

public interface ShopManagerService {
    ShopManager save(ShopManager shopManager) throws ShopManagerAlreadyExistInShop;

    String delete(String id);
    List<ShopManager> findShopManagersByShopId(String shopId);
}
