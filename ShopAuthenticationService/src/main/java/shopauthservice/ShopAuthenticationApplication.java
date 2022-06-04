package shopauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableEurekaClient
public class ShopAuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopAuthenticationApplication.class,args);
    }
    @Bean
    public CorsFilter corsFilter() {
        System.out.println("Filter chain");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false); // you USUALLY want this
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        System.out.println("Filter chain");
        return new CorsFilter(source);
    }
}
