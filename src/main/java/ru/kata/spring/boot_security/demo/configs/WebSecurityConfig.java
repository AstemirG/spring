package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class  WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    @Autowired
    private UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {    //все это правила оптимизации,читаются сверху вниз
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll() //  странички куда пускаем всех
                .antMatchers("/", "/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers( "/user/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .anyRequest().authenticated()  // с помощью этого чтоб доступ был для авторизированных юзеров
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()  //.addLogoutHandler((LogoutHandler) successUserHandler)????
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder amb) {
        amb.authenticationProvider(daoAuthenticationProvider());
    }
}