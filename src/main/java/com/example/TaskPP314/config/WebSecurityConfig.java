package com.example.TaskPP314.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginSuccessHandler loginSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, UserDetailsService userDetailsService,
                             PasswordEncoder bCryptPasswordEncoder) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .mvcMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .mvcMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(bCryptPasswordEncoder);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}