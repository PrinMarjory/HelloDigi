package fr.diginamic.HelloDigi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

/**
 * Représente un département par son identifiant, son nom et son code 
 * 
 * @author Marjory PRIN
 */
@Entity
public class Departement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nom;
	@NotNull
	@Column(unique = true)
	private String code;
	@OneToMany(mappedBy = "departement")
	private List<Ville> villes = new ArrayList<>();
	
	/** Constructeur pour JPA */
	public Departement() {
		super();
	}
	
	/** Constructeur pour l'initialisation de la base
	 * @param nom
	 * @param code
	 */
	public Departement(String nom, String code) {
		super();
		this.nom = nom;
		this.code = code;
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/** Setter
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/** Getter
	 * @return the villes
	 */
	public List<Ville> getVilles() {
		return villes;
	}

	/** Setter
	 * @param villes the villes to set
	 */
	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}
	
	
}
