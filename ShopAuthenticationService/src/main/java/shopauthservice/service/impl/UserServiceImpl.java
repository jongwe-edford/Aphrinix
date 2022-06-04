package shopauthservice.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopauthservice.exception.AccountWithEmailAlreadyExist;
import shopauthservice.exception.PasswordResetTokenExpiredException;
import shopauthservice.exception.RefreshTokenExpired;
import shopauthservice.exception.UserNotFoundException;
import shopauthservice.models.AccountActivationToken;
import shopauthservice.models.Role;
import shopauthservice.models.User;
import shopauthservice.models.enums.UserRole;
import shopauthservice.payload.request.LoginRequest;
import shopauthservice.payload.request.PasswordResetRequest;
import shopauthservice.payload.request.RegistrationRequest;
import shopauthservice.payload.response.LoginResponse;
import shopauthservice.repository.RoleRepository;
import shopauthservice.repository.UserRepository;
import shopauthservice.service.AccountActivationService;
import shopauthservice.service.PasswordResetService;
import shopauthservice.service.RefreshTokenService;
import shopauthservice.service.UserService;
import shopauthservice.util.JwtUtil;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;
    private final AccountActivationService accountActivationService;
    private final PasswordResetService passwordResetService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public String register(RegistrationRequest request) throws AccountWithEmailAlreadyExist {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AccountWithEmailAlreadyExist("An account already exist");
        }

        Set<Role> roleSet = new HashSet<>();
        var role = new Role(UserRole.ROLE_SHOP_ADMIN);
        roleSet.add(role);

        User user = User
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastname(request.getLastname())
                .firstname(request.getFirstname())
                .enabled(true)
                .roles(roleSet)
                .build();
        userRepository.save(user);

        AccountActivationToken activationToken = AccountActivationToken
                .builder()
                .token(UUID.randomUUID().toString())
                .email(user.getEmail())
                .createdAt(Instant.now())
                .used(false)
                .expiration(Instant.now().plus(15, ChronoUnit.MINUTES))
                .build();
        accountActivationService.saveToken(activationToken);
        return "Registration successful";
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        ResponseEntity<?> responseEntity = null;
        try {
            Optional<User> user2 = userRepository.findByEmail(loginRequest.getEmail());

            if (user2.isEmpty())
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("No account with the provided email");
            if (!passwordEncoder.matches(loginRequest.getPassword(), user2.get().getPassword()))
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Invalid password");
            if (!user2.get().isEnabled()) {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Please verify your account to login");
            }
            Authentication authentication = manager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getEmail(), loginRequest.getPassword()
                            )
                    );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            System.out.println(user.toString());
            ResponseCookie responseCookie = jwtUtil.generateJwtCookie(user);
            responseEntity = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(new LoginResponse(responseCookie.getValue(), refreshTokenService.generateToken(user.getEmail())));
        } catch (BadCredentialsException | ExpiredJwtException e) {
            responseEntity = ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Email or password invalid");
        }
        return responseEntity;
    }

    @Override
    public User getUserByEmail(String email) throws AccountNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new AccountNotFoundException("No account found"));
    }

    @Override
    public String activateAccount(String token) {
        AccountActivationToken activationToken = accountActivationService
                .getAccountActivationTokenFromToken(token);
        User user = userRepository.findByEmail(activationToken.getEmail()).get();
        user.setEnabled(true);
        userRepository.save(user);
        activationToken.setUsed(true);
        accountActivationService.saveToken(activationToken);
        return "Account activated";
    }

    @Override
    public User getCurrentUserFromToken() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null)
            return null;
        return user;
    }

    @Override
    public void forgotPassword(String email) throws UserNotFoundException {
        passwordResetService.forgotPassword(email);
    }

    @Override
    public void resendPasswordResetToken(String email) throws UserNotFoundException {
        passwordResetService.resendResetToken(email);
    }

    @Override
    public void resetPassword(String token, PasswordResetRequest passwordResetRequest) throws UserNotFoundException, PasswordResetTokenExpiredException {
        passwordResetService.resetPassword(token, passwordResetRequest);
    }

    @Override
    public void resendActivationToken(String email) {
        accountActivationService.resendActivationToken(email);
    }

    @Override
    public LoginResponse refreshToken(String token) throws RefreshTokenExpired {
        LoginResponse loginResponse = refreshTokenService.refreshToken(token);
        System.out.println(loginResponse.toString());
        return loginResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
