package fr.diginamic.HelloDigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.HelloDigi.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	
	public UserAccount findByUsername(String username);
}
