package dev.ershov.vd.secure;

import dev.ershov.vd.service.UsersWebServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersWebServices usersWebServices;

    public WebSecurityConfig(UsersWebServices usersWebServices) {
        this.usersWebServices = usersWebServices;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST ,"/botTgTable");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/iknt", "/iknt/search", "/ipmt", "/ipmt/search", "/ici",
                        "/ici/search", "/gi", "/gi/search", "/immit", "/immit/search", "/ipmm",
                        "/ipmm/search", "/ifnit", "/ifnit/search", "/ibcib", "/ibcib/search",
                        "/ia", "/ia/search", "/icpo", "/icpo/search").hasRole("ADMIN")
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
                .antMatchers(HttpMethod.POST ,"/botTgTable").permitAll()
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
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}