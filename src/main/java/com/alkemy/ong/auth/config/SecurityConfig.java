package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.filter.JwtRequestFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/contacts").permitAll();


        //Auth routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/register").permitAll();

        //SWAGGER route
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/docs").permitAll();

        //You have to login to see next routes
        http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();

        ////////////////////////////////////
        //          Admin Routes          //
        ////////////////////////////////////

        http.authorizeRequests().antMatchers("/**").hasAuthority("ADMIN");

        //Don't add any routes below
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

        http.addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
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
