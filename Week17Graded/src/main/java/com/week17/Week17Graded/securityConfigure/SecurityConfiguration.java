package com.week17.Week17Graded.securityConfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.week17.Week17Graded.service.UserCredService;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		// '/**' means any url starting with '/'
		http.csrf().disable().authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).formLogin();
		return http.build();
				
	}

	// for DB
	@Bean
	// Here after instead of user detail service(InMemory) we need to use
	// UserCredService(User Credentails)
	public UserDetailsService userDetailsService() {
		return new UserCredService();
	}

	// This to encode the password before storing it to the db
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	// Defining how the authentication must happen
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
}
