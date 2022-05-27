package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.filter.JwtRequestFilter;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().antMatchers("/auth/register", "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/contacts").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/users/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/slides").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/slides/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/slides/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/slides/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/slides").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/activities").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/activities/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/organization/public").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/organization/public").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/news").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/news").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/news/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/news/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/news/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/categories").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/storage/uploadFile").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/storage/deleteFile").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/comments").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/comments").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/comments").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/comments").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/comments/posts/{newsId}/comments").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/members/").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/members/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/members/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/members/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/testimonials").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/testimonials/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/testimonials/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/testimonials").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/contacts").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/contacts").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

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
