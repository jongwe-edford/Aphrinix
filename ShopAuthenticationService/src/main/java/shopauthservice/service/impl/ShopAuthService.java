package shopauthservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shopauthservice.repository.UserRepository;

@Service
@AllArgsConstructor
public class ShopAuthService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email "+email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }





}
