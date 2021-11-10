package com.spark.toy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User
                        .builder()
                        .username("user1@mail.com")
                        .password(passwordEncoder().encode("1111"))
                        .roles("USER"))
                .withUser(User.builder()
                        .username("admin@mail.com")
                        .password(passwordEncoder().encode("3333"))
                        .roles("ADMIN"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request->
                        request
                                .antMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/main", false)
                        .failureUrl("/login")
                        .usernameParameter("email")
                    //    .passwordParameter("password")
                )
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                ;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**", "/css/**","/js/**", "/img/**", "/bootstrap/vendor/**", "/bootstrap/scss/**");
    //    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
