package shopauthservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopauthservice.exception.AccountWithEmailAlreadyExist;
import shopauthservice.exception.PasswordResetTokenExpiredException;
import shopauthservice.exception.RefreshTokenExpired;
import shopauthservice.exception.UserNotFoundException;
import shopauthservice.payload.request.LoginRequest;
import shopauthservice.payload.request.PasswordResetRequest;
import shopauthservice.payload.request.RegistrationRequest;
import shopauthservice.payload.response.LoginResponse;
import shopauthservice.service.UserService;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(path = "shop/auth")
public class ShopAuthServiceController {
    private final UserService shopAuthService;

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return shopAuthService.login(loginRequest);
    }

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) throws AccountWithEmailAlreadyExist {
        return ResponseEntity.ok(shopAuthService.register(registrationRequest));
    }

    @PostMapping(path = "activate")
    public ResponseEntity<?> activateAccount(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(shopAuthService.activateAccount(token));
    }

    @PostMapping(path = "forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam(value = "email") String token) throws UserNotFoundException {
        shopAuthService.forgotPassword(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam(value = "token") String token, @RequestBody PasswordResetRequest passwordResetRequest) throws UserNotFoundException, PasswordResetTokenExpiredException {
        shopAuthService.resetPassword(token, passwordResetRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "resend-password-token")
    public ResponseEntity<Void> resendPasswordResetToken(@RequestParam(value = "email") String token) throws UserNotFoundException, PasswordResetTokenExpiredException {
        shopAuthService.resendPasswordResetToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "resend-activation-token")
    public ResponseEntity<Void> resendActivationToken(@RequestParam(value = "email") String token) throws UserNotFoundException, PasswordResetTokenExpiredException {
        shopAuthService.resendActivationToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping(path = "u")
    public ResponseEntity<?> getUser(@RequestParam(value = "token") String token) throws UserNotFoundException {
        return ResponseEntity.ok(shopAuthService.getUser(token));
    }

    @GetMapping(path = "{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) throws AccountNotFoundException {
        return ResponseEntity.ok(shopAuthService.getUserByEmail(email));
    }

    @PostMapping(path = "refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam("token") String token) throws  RefreshTokenExpired {
        return ResponseEntity.ok(shopAuthService.refreshToken(token));
    }

}
