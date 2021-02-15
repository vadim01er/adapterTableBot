package dev.ershov.vd.secure;

import dev.ershov.vd.service.UsersWebServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersWebServices usersWebServices;

    public WebSecurityConfig( UsersWebServices usersWebServices) {
        this.usersWebServices = usersWebServices;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST ,"/botTgTable");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/iknt", "/iknt/search").hasRole("iknt")
                .antMatchers("/ipmt", "/ipmt/search").hasRole("ipmt")
                .antMatchers("/ici", "/ici/search").hasRole("ici")
                .antMatchers("/gi", "/gi/search").hasRole("gi")
                .antMatchers("/immit", "/immit/search").hasRole("immit")
                .antMatchers("/ipmm", "/ipmm/search").hasRole("ipmm")
                .antMatchers("/ifnit", "/ifnit/search").hasRole("ifnit")
                .antMatchers("/ibcib", "/ibcib/search").hasRole("ibcib")
                .antMatchers("/ia", "/ia/search").hasRole("ia")
                .antMatchers("/icpo", "/icpo/search").hasRole("icpo")
                .antMatchers("/").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(usersWebServices);
        return daoAuthenticationProvider;
    }
}
