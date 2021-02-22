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
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/iknt", "/iknt/search", "/download/iknt").hasAnyRole("ADMIN", "iknt")
                    .antMatchers("/ipmt", "/ipmt/search", "/download/ipmt").hasAnyRole("ADMIN", "ipmt")
                    .antMatchers("/ici", "/ici/search", "/download/ici").hasAnyRole("ADMIN", "ici")
                    .antMatchers("/gi", "/gi/search", "/download/gi").hasAnyRole("ADMIN", "gi")
                    .antMatchers("/immit", "/immit/search", "/download/immit").hasAnyRole("ADMIN", "immit")
                    .antMatchers("/ipmm", "/ipmm/search", "/download/ipmm").hasAnyRole("ADMIN", "ipmm")
                    .antMatchers("/ifnit", "/ifnit/search", "/download/ifnit").hasAnyRole("ADMIN", "ifnit")
                    .antMatchers("/ibcib", "/ibcib/search", "/download/ibcib").hasAnyRole("ADMIN", "ibcib")
                    .antMatchers("/ia", "/ia/search", "/download/ia").hasAnyRole("ADMIN", "ia")
                    .antMatchers("/icpo", "/icpo/search", "/download/icpo").hasAnyRole("ADMIN", "icpo")
                .antMatchers("/ikizi", "/ikizi/search", "/download/ikizi").hasAnyRole("ADMIN", "ikizi")
                    .antMatchers("/").hasAnyRole("ADMIN", "iknt", "ipmt", "ici", "gi", "immit",
                        "ipmm", "ifnit", "ibcib", "ia", "icpo", "ikizi")
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersWebServices)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(usersWebServices);
//        return daoAuthenticationProvider;
//    }
}
