package shops.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shops.exception.ShopManagerAlreadyExistInShop;
import shops.model.ShopManager;
import shops.repository.ShopManagerRepository;
import shops.service.ShopManagerService;

@Service
@AllArgsConstructor
public class ShopManagerServiceImpl implements ShopManagerService {
    private final ShopManagerRepository shopManagerRepository;
    @Override
    public ShopManager save(ShopManager shopManager) throws ShopManagerAlreadyExistInShop {
        if (shopManagerRepository.existsByEmail(shopManager.getEmail())){
            throw new ShopManagerAlreadyExistInShop("Shop manager with the provided credentials already exists");
        }

       return shopManagerRepository.save(shopManager);
    }

    @Override
    public String delete(String id) {
        shopManagerRepository.deleteById(id);
        return "Shop manager "+id+" deleted successfully";
    }
}
