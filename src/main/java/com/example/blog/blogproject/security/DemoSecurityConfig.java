package com.example.blog.blogproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public UserDetailsManager userDetailManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username =?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, role from role where username =?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer

                                .requestMatchers("/blog/","/registerForm","/saveUser","/api/**").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/posts/").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/posts/").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/posts/").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/comments/").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/comments/").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/comments/").permitAll()
                                .requestMatchers("/blog/readForm/**").permitAll()
                                .requestMatchers("/blog/readForm/**").permitAll()
                                .requestMatchers("/blog/filter/**").permitAll()
                                .requestMatchers("/blog/createpost/**").hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog/savepost/**").permitAll()
                               .requestMatchers("/blog/updateForm/**", "/blog/updatePost/**").hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog/deleteForm/**", "/blog/deletePost/**").hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog/addComments/**").permitAll()
                                .requestMatchers("/blog/updateComment/**").hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog/saveupdateComment/**").hasAnyRole("AUTHOR", "ADMIN")
                                .requestMatchers("/blog/deleteComment/**").hasAnyRole("AUTHOR", "ADMIN")
                                .anyRequest().authenticated()

                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/blog/")
                                .permitAll()
                )
                .logout(logout ->
                          logout
                                    .permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access_denied")
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf ->
                        csrf.disable()
                );

        return http.build();
    }




}