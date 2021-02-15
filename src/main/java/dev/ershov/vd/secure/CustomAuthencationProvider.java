package dev.ershov.vd.secure;

import dev.ershov.vd.service.UsersWebServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private UsersWebServices dao;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        //получаем пользователя
        UserDetails myUser = dao.loadUserByUsername(userName);
        //смотрим, найден ли пользователь в базе
        System.out.println(password);
        System.out.println(myUser.getPassword());
        System.out.println(password.equals(myUser.getPassword()));

        if (myUser == null) {
            throw new BadCredentialsException("Unknown user "+userName);
        }
        if (!password.equals(myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }

        return new UsernamePasswordAuthenticationToken(myUser, password, myUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
