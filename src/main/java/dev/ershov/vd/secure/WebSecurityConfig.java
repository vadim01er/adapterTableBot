package dev.ershov.vd.secure;

import dev.ershov.vd.service.UsersWebServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersWebServices usersWebServices;

    public WebSecurityConfig( UsersWebServices usersWebServices) {
        this.usersWebServices = usersWebServices;
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers(HttpMethod.POST ,"/botTgTable");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin-2")
                .password(passwordEncoder().encode("R04jYKHKx6")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().and()
                .authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers("/iknt", "/iknt/search").hasAnyRole("ADMIN", "iknt")
                .antMatchers("/ipmt", "/ipmt/search").hasAnyRole("ADMIN", "ipmt")
                .antMatchers("/ici", "/ici/search").hasAnyRole("ADMIN", "ici")
                .antMatchers("/gi", "/gi/search").hasAnyRole("ADMIN", "gi")
                .antMatchers("/immit", "/immit/search").hasAnyRole("ADMIN", "immit")
                .antMatchers("/ipmm", "/ipmm/search").hasAnyRole("ADMIN", "ipmm")
                .antMatchers("/ifnit", "/ifnit/search").hasAnyRole("ADMIN", "ifnit")
                .antMatchers("/ibcib", "/ibcib/search").hasAnyRole("ADMIN", "ibcib")
                .antMatchers("/ia", "/ia/search").hasAnyRole("ADMIN", "ia")
                .antMatchers("/icpo", "/icpo/search").hasAnyRole("ADMIN", "icpo")
                .antMatchers("/").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .permitAll();
    }


    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(usersWebServices);
        return daoAuthenticationProvider;
    }
}
