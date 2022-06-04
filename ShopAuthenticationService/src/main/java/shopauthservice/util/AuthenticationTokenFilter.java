package shopauthservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import shopauthservice.service.impl.ShopAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private  ShopAuthService shopAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseJwtToken(request);
            if (token != null && jwtUtil.validateToken(token)) {
                String email = jwtUtil.getEmailFromToken(token);
                UserDetails userDetails = shopAuthService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        filterChain.doFilter(request, response);

    }

    private String parseJwtToken(HttpServletRequest request) {
        return jwtUtil.getJwtFromCookies(request);
    }
}
