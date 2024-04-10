package fr.diginamic.HelloDigi.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Représente une ville par son identifiant, son nom et son nombre d'habitants
 * 
 * @author Marjory PRIN
 */
@Entity
public class Ville {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private int nbHabitants;
	@ManyToOne(cascade = CascadeType.ALL)
	@Nonnull
	Departement departement;

	/** Constructeur pour JPA */
	public Ville() {
		super();
	}
	
	/** Constructeur pour l'initialisation de la base
	 * @param nom
	 * @param nbHabitants
	 * @param departement
	 */
	public Ville(String nom, int nbHabitants, Departement departement) {
		super();
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.departement = departement;
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

	/** Getter
	 * @return the departement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/** Setter
	 * @param departement the departement to set
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	
}
