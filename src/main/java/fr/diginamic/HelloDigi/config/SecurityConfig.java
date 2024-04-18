package fr.diginamic.HelloDigi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import fr.diginamic.HelloDigi.repository.UserAccountRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build());
		userDetailsManager.createUser(User.withDefaultPasswordEncoder()
			.username("admin")
			.password("password")
			.roles("ADMIN")
			.build());
		return userDetailsManager;
		}
	*/
	
	@Bean 
	public  UserDetailsService userdetailsService(UserAccountRepository userAccountRepository) {
		return username -> userAccountRepository.findByUsername(username).asUser();
	}
	
	
	@Bean
	SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.requestMatchers("/login").permitAll() // permet l'accès au login à tous
			.requestMatchers("/", "/index", "/villes/**").authenticated()
			.requestMatchers(HttpMethod.GET, "/api/villes/**").authenticated() // protection api
			.requestMatchers(HttpMethod.POST, "/api/villes/**").hasRole("ADMIN")
			.requestMatchers("/admin").hasRole("ADMIN")

			.anyRequest().denyAll() //tout le reste est sécurisé
			.and()
			.formLogin()
			.and()
			.httpBasic();
		return http.build();
	}
}
