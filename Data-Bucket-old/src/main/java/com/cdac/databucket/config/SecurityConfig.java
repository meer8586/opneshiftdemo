package com.cdac.databucket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	  
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(getPasswordEncoder());
		return provider;
		
	}
	
	
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	
	    	 http.csrf().disable()
             .authorizeRequests()
             .antMatchers("/","/login","/signup","/downloadSharedFolder/**","/about","/contact","/js/**","/css/**","/images/**").permitAll()
             .anyRequest().authenticated()
             .and().formLogin()
             .loginPage("/login")
             .defaultSuccessUrl("/home")
             .and()
             .logout()
             .logoutUrl("/logout")
             .logoutSuccessUrl("/")
             .permitAll();
	    	
	    	 http.authenticationProvider(daoAuthenticationProvider());
	    	 
	    	return http.build();
	    }
	    
	    
	    @Bean
	    public BCryptPasswordEncoder getPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
