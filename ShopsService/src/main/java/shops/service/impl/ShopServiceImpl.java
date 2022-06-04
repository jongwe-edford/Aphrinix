package shops.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shops.exception.EmailAlreadyExistException;
import shops.model.Shop;
import shops.model.User;
import shops.payload.request.ShopRegistrationRequest;
import shops.repository.ShopRepository;
import shops.service.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;

    @Override
    public Shop saveShop(ShopRegistrationRequest request, HttpServletRequest servletRequest) throws EmailAlreadyExistException {
        String url = "http://SHOP-AUTH-SERVICE/shop/auth/{email}";
        User user = restTemplate.getForObject(url, User.class, request.getEmail());
        if (shopRepository.existsByShopAdmin(user))
            throw new EmailAlreadyExistException("A shop with the provided email already exist");

        System.out.println(user.toString());

        Shop shop = Shop
                .builder()
                .name(request.getName())
                .shopAdmin(user)
                .closingTime(request.getClosingTime())
                .openingTime(request.getOpeningTime())
                .shopId(UUID.randomUUID().toString())
                .phoneNumber(request.getPhoneNumber())
                .registrationIp(servletRequest.getRemoteAddr())
                .phoneNumberVerified(false)
                .creationDate(LocalDateTime.now())
                .build();

        return shopRepository.save(shop);
    }

    @Override
    public Shop getShopByID(String id) {
        return shopRepository.findByShopId(id);
    }

    @Override
    public Shop getShopByEmail(String email) {
        return null;
    }

    @Override
    public List<Shop> getAllShops() {
        return null;
    }
}
