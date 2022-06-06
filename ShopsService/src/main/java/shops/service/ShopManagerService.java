package shops.service;

import shops.exception.ShopManagerAlreadyExistInShop;
import shops.model.ShopManager;

public interface ShopManagerService {
     ShopManager save(ShopManager shopManager) throws ShopManagerAlreadyExistInShop;
    String delete(String id);
}
