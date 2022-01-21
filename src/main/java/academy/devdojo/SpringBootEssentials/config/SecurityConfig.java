package academy.devdojo.SpringBootEssentials.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * BasicAuthenticationFilter
     * UserNameAuthenticationFilter
     * DefaultLoginPageGenerationFilter
     * DefaultLogoutPageGenerationFilter
     * FilterSecurityInterceptor
     * Authentication -> Authorization
     * @param http
     * @throws Exception
     */



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
//                csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//               .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encode{}", passwordEncoder.encode("test"));
        auth.inMemoryAuthentication()
                .withUser("Luccas")
                .password(passwordEncoder.encode("lu072324"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("LuizMiguel")
                .password(passwordEncoder.encode("lu072324"))
                .roles("USER");
    }
}
