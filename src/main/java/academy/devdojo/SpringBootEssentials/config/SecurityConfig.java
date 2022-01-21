package academy.devdojo.SpringBootEssentials.config;

import academy.devdojo.SpringBootEssentials.service.LuccasUserDetailsService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LuccasUserDetailsService luccasUserDetailsService;
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
                .antMatchers("/animes/admin/**").hasRole("ADMIN")
                .antMatchers("/animes/**").hasRole("USER")
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
        log.info("Password encode{}", passwordEncoder.encode("academy"));
        auth.inMemoryAuthentication()
                .withUser("Luccas2")
                .password(passwordEncoder.encode("lu072324"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("LuizMiguel2")
                .password(passwordEncoder.encode("lu072324"))
                .roles("USER");
        auth.userDetailsService(luccasUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
