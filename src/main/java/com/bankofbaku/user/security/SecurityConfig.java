//package com.bankofbaku.user.security;
//
//import com.bankofbaku.user.entity.User;
//import com.bankofbaku.user.services.UserService;
//import com.bankofbaku.user.services.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(authenticationProvider());
//    }
//    protected void configure(HttpSecurity http) throws Exception{
//
//        //securityConfigurer.configure(http);
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//        //Allow swagger and actuator
//        //Disallow all requests by default unless explicitly defined in submodules
//        http.authorizeRequests().anyRequest().access(authorities(ROLE_SUPER_USER.name(), ROLE_ADMIN.name()));
//        // Apply AuthRequestFilter
//        http.apply(authFilterConfigurerAdapter);
//
//
//
//
//        http.authorizeRequests()
//                .antMatchers("api/users/new").hasAnyAuthority("USER","CREATOR","EDITOR","ADMIN")
//                .antMatchers("api/users/new").hasAnyAuthority("ADMIN","CREATOR")
//                .antMatchers("/update/**").hasAnyAuthority("ADMIN","EDITOR")
//                .antMatchers("/delete/**").hasAuthority("ADMIN")
//                //.anyRequest().authenticated()
//                .and().formLogin().permitAll().and().logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/403")
//                .and()
//                .csrf().disable();
//    }

//}
