package shops.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shops.model.ShopManager;
import shops.service.ShopManagerService;

import java.util.List;

@RestController
@RequestMapping(path = "manager")
@AllArgsConstructor
public class ShopManagerController {

    private  final ShopManagerService shopManagerService;

    @GetMapping(path = "list/{id}")
    public ResponseEntity<List<ShopManager>> shopManagers(@PathVariable("id") String id){
        return  ResponseEntity.ok(shopManagerService.findShopManagersByShopId(id));
    }
}
