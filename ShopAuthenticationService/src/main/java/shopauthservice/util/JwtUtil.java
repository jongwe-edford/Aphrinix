package shopauthservice.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import shopauthservice.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil implements Serializable {
    @Value("${shop.admin.jwt.secret}")
    private  String JWT_TOKEN_SECRET;
    @Value("${shop.admin.jwt.expiration}")
    private  String JWT_TOKEN_EXPIRATION;
    @Value("${shop.admin.jwt.cookieName}")
    private String JWT_COOKIE_NAME;


    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie= WebUtils.getCookie(request,JWT_COOKIE_NAME);
        if (cookie!=null)
            return cookie.getValue();
        else
            return null;
    }

    public ResponseCookie generateJwtCookie(User user){
        String token=generateTokenFromEmail(user.getEmail());
        return ResponseCookie.from(JWT_COOKIE_NAME,token).maxAge(24*60*60).build();
    }

    public String getEmailFromToken(String token){
        return Jwts.parser()
                .setSigningKey(JWT_TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_TOKEN_SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException |MalformedJwtException|ExpiredJwtException|UnsupportedJwtException|IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }
    private String generateTokenFromEmail(String email) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 120);
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,JWT_TOKEN_SECRET)
                .compact();
    }

}
