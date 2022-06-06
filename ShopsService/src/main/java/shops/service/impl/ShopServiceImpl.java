package shops.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import shops.exception.EmailAlreadyExistException;
import shops.exception.ShopManagerAlreadyExistInShop;
import shops.mail.model.Email;
import shops.mail.service.MailService;
import shops.model.Shop;
import shops.model.ShopManager;
import shops.model.User;
import shops.payload.request.ShopRegistrationRequest;
import shops.repository.ShopRepository;
import shops.service.ShopManagerService;
import shops.service.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;
    private final MailService mailService;
    private final ShopManagerService shopManagerService;

    @Override
    public Shop saveShop(ShopRegistrationRequest request, HttpServletRequest servletRequest, String authorizationToken) throws EmailAlreadyExistException {
        String token = authorizationToken.substring(7);
        System.out.println("Token   " + token);
        String url = "http://SHOP-AUTH-SERVICE/shop/auth/u?token={token}";
        User user = restTemplate.getForObject(url, User.class, token);
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

    @Override
    public void sendShopManagerRegistrationLink(String receiver, String shopId) {
        Shop shop = shopRepository.findByShopId(shopId);
        Email email = Email
                .builder()
                .receiver(receiver)
                .sender(shop.getShopAdmin().getEmail())
                .sendTime(LocalDateTime.now())
                .subject("Registration as a shop manager")
                .build();
        System.out.println(email);
        String url = "http://localhost:8812/shop/auth/shop?id=" + shopId;

        Context context = new Context();
        context.setVariable("url", url);
        context.setVariable("shop", shop);
        context.setVariable("receiver", email.getReceiver());
        mailService.sendHtml("add-shop-admin", email, context);
    }

    @Override
    public String addShopManager(User user, String shopId) throws ShopManagerAlreadyExistInShop {
        Shop shop = shopRepository.findByShopId(shopId);
        System.out.println(shop.toString());
        ShopManager shopManager = ShopManager
                .builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .shopId(shopId)
                .build();
        ShopManager manager = shopManagerService.save(shopManager);
        List<ShopManager> managers = shop.getShopManagers();
        if (managers!=null)
            managers.add(manager);
        else {
            managers=new ArrayList<>();
            managers.add(manager);
        }
        shop.setShopManagers(managers);
         shopRepository.save(shop);

         return "Manager added successfully";
    }
}
