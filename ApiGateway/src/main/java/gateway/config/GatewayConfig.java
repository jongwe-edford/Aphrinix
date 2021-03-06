package gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("shop-auth", r -> r.path("/shop/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SHOP-AUTH-SERVICE"))
                .route("shops", r -> r.path("/shops/**", "/manager/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SHOPS-SERVICE"))
                .route("products", r -> r.path("/products/**", "/categories/**")
                        .uri("lb://PRODUCTS-SERVICE"))
                .route("images", r -> r.path("/images/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://IMAGES-SERVICE"))
                .build();
    }

}
