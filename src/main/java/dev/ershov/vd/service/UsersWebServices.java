package dev.ershov.vd.service;

import dev.ershov.vd.entities.UserWebRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersWebServices implements UserDetailsService {

    private final UserWebRepository userRepository;

    public UsersWebServices(UserWebRepository userRepository) {
        this.userRepository = userRepository;
    }
//$2a$10$Twhxxtr37jCg5xePHOlQsuZ7T9ysZ41XSsqv26cENK9ktHdR2yjx6
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        return user;
    }
}
