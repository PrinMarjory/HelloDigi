package fr.diginamic.HelloDigi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	List<GrantedAuthority> authorities = new ArrayList<>();
	
	/** Constructeur pour JPA */
	public UserAccount() {
		super();
	}

	/** Constructeur
	 * @param username
	 * @param password
	 * @param authorities
	 */
	public UserAccount(String username, String password, String... authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = Arrays.stream(authorities).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
	}
	
	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/** Setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/** Getter
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/** Setter
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/** Getter
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/** Setter
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/** Getter
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	/** Setter
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public UserDetails asUser() {
		return User.withDefaultPasswordEncoder()
			.username(getUsername())
			.password(getPassword())
			.authorities(getAuthorities())
			.build();
	}

}
