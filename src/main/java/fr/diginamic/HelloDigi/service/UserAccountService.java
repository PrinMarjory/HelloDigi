package fr.diginamic.HelloDigi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.UserAccount;
import fr.diginamic.HelloDigi.repository.UserAccountRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserAccountService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@PostConstruct
	private void init() {
		if (userAccountRepository.findByUsername("user") == null) {
			UserAccount user1 = new UserAccount("user", "user", "ROLE_USER");
			userAccountRepository.save(user1);
		}
		if (userAccountRepository.findByUsername("admin") == null) {
			UserAccount user2 = new UserAccount("admin", "admin", "ROLE_ADMIN");
			userAccountRepository.save(user2);
		}
	}
}
