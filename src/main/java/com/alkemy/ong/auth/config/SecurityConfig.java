package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Public Routes
        //Organization routes
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/organization/public").permitAll();

        //Auth routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/register").permitAll();

        //You have to login to see next routes
        http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();

        //Authenticated and Role dependent
        //Testimonial routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "testimonials").hasRole("ADMIN");

        //Activity routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/activities").hasRole("ADMIN");

        //News routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/news").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/news/{id}").hasRole("ADMIN");

        //Categories routes
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/categories").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN");

        //User routes
        http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN");

        //Slides routes
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/slides/{id}").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/slides/{id}").hasRole("ADMIN");


        //Don't add any routes below
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
