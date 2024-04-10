package fr.diginamic.HelloDigi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Représente une ville par son identifiant, son nom et son nombre d'habitants
 * 
 * @author Marjory PRIN
 */
@Entity
@Table(name = "VILLE")
public class Ville {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private int nbHabitants;
	
	/** Constructeur pour JPA
	 * @param nom
	 * @param nbHabitants
	 */
	public Ville() {
		super();
	}
	
	/** Constructeur pour l'initialisation de la base
	 * @param nom
	 * @param nbHabitants
	 */
	public Ville(String nom, int nbHabitants) {
		super();
		this.nom = nom;
		this.nbHabitants = nbHabitants;
	}
	
	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @return the nbHabitants
	 */
	public int getNbHabitants() {
		return nbHabitants;
	}

	/** Setter
	 * @param nbHabitants the nbHabitants to set
	 */
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	
	
}
