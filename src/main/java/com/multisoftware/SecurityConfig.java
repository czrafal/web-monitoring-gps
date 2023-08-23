/*
package com.multisoftware;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

*/
/**
 * Spring Security Configuration.
 *
 * @author Marcelo Fernandes
 *//*

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    */
/*

    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
        }
    }*//*

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http
                    .userDetailsService(userDetailsService())
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/**.jsf").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.jsf")
                    .permitAll()
                    .failureUrl("/login.jsf?error=true")
                    .defaultSuccessUrl("/system/newVehicle.jsf")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login.jsf")
                    .deleteCookies("JSESSIONID");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager result = new InMemoryUserDetailsManager();
        result.createUser(User.withDefaultPasswordEncoder().username("persapiens").password("123").authorities("ROLE_ADMIN").build());
        result.createUser(User.withDefaultPasswordEncoder().username("nyilmaz").password("qwe").authorities("ROLE_USER").build());
        return result;
    }
}*/
