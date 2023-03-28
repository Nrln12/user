package com.bankofbaku.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/users/").hasAnyAuthority("USER","CREATOR","EDITOR","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/users/new").hasAnyAuthority("ADMIN","CREATOR")
                .antMatchers(HttpMethod.PUT,"/api/users/update/**").hasAnyAuthority("ADMIN","EDITOR")
                .antMatchers(HttpMethod.DELETE,"/api/users/delete/**").hasAuthority("ADMIN")
                .anyRequest().authenticated().and().csrf().disable().httpBasic();
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

}
