package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.repository.DepartementRepository;
import fr.diginamic.HelloDigi.repository.VilleRepository;
import jakarta.annotation.PostConstruct;

@Service
public class VilleService {
	
	@Autowired
	VilleRepository villeRepository;
	DepartementRepository departementRepository;
	
	@PostConstruct
	public void init() {
		//Création de la table Ville à l'initialisation
		villeRepository.save(new Ville("Paris",2161000,new Departement("Paris","75")));
		villeRepository.save(new Ville("Marseille",2035000,new Departement("Bouches-du-Rhône","13")));
		villeRepository.save(new Ville("Lyon",1605000,new Departement("Rhône","69")));
		villeRepository.save(new Ville("Nantes",303382,new Departement("Loire-Atlantique","44")));
	}	

	public List<Ville> extractVilles() {
		return (List<Ville>) villeRepository.findAll();
	}
	
	public Ville extractVille(Long id) {
		return villeRepository.findById(id).get();
	}
	
	public Ville extractVille(String nom) {
		return villeRepository.findByNom(nom);
	}
	
	public boolean insertVille(Ville ville) throws FunctionalException {
		Ville v = villeRepository.findByNom(ville.getNom());
		if (v != null && v.getDepartement().equals(ville.getDepartement())) {
			return false;
		} else {
			if(ville.getNbHabitants()<10) {
				throw new FunctionalException("La ville doit avoir au moins 10 habitants");
			}
			if(ville.getNom().length()<2) {
				throw new FunctionalException("Le nom de la ville doit contenir au moins deux lettres");
			}
			if(ville.getDepartement().getCode().length()<2) {
				throw new FunctionalException("Le code du département doit contenir au moins deux caractères");
			}
			villeRepository.save(ville);
			return true;
		}
	}
	
	public boolean modifierVille(Ville ville) throws FunctionalException {
		Ville villeFromDB = villeRepository.findById(ville.getId()).get();
		if (villeFromDB != null) {
			if(ville.getNbHabitants()<10) {
				throw new FunctionalException("La ville doit avoir au moins 10 habitants");
			}
			if(ville.getNom().length()<2) {
				throw new FunctionalException("Le nom de la ville doit contenir au moins deux lettres");
			}
			if(ville.getDepartement().getCode().length()<2) {
				throw new FunctionalException("Le code du département doit contenir au moins deux caractères");
			}
			villeFromDB.setNom(ville.getNom());
			villeFromDB.setNbHabitants(ville.getNbHabitants());
			villeRepository.save(villeFromDB);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean supprimerVille(Long idVille) {
		Ville villeFromDB = villeRepository.findById(idVille).get();
		if (villeFromDB != null) {
			villeRepository.deleteById(idVille);
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<Ville> findByNomStartingWith(String prefix) throws FunctionalException {
		List<Ville> resultat = villeRepository.findByNomStartingWith(prefix);
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville dont le nom commence par "+prefix+" n’a été trouvée");
		} else {
			return resultat;
		}
	}
    
    public List<Ville> findByNbHabitantsGreaterThan(int minPopulation) throws FunctionalException {
    	List<Ville> resultat = villeRepository.findByNbHabitantsGreaterThan(minPopulation);
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville n’a une population supérieure à "+minPopulation);
		} else {
			return resultat;
		}
    }
    
    public List<Ville> findByNbHabitantsBetween(int minPopulation, int maxPopulation) throws FunctionalException {
    	List<Ville> resultat = villeRepository.findByNbHabitantsBetween(minPopulation, maxPopulation);
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville n’a une population comprise entre "+minPopulation+" et "+maxPopulation);
		} else {
			return resultat;
		}
    }
    
    public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String departementCode, int minPopulation) throws FunctionalException {
    	List<Ville> resultat = villeRepository.findByDepartementCodeAndNbHabitantsGreaterThan(departementCode, minPopulation);
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville n’a une population supérieure à "+minPopulation+" dans le département "+departementCode);
		} else {
			return resultat;
		}
    }
    
    public List<Ville> findByDepartementCodeAndNbHabitantsBetween(String departementCode, int minPopulation, int maxPopulation) throws FunctionalException {
    	List<Ville> resultat = villeRepository.findByDepartementCodeAndNbHabitantsBetween(departementCode, minPopulation, maxPopulation);
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville n’a une population comprise entre "+minPopulation+" et "+maxPopulation+" dans le département "+departementCode);
		} else {
			return resultat;
		}
    }
    
    public List<Ville> findByDepartementCodeOrderByNbHabitantsDesc(String departementCode, Integer size) throws FunctionalException {
    	List<Ville> resultat = villeRepository.findByDepartementCodeOrderByNbHabitantsDesc(departementCode, Pageable.ofSize(size)).getContent();
		if (resultat.isEmpty()) {
			throw new FunctionalException("Aucune ville n'a été trouvée dans le département "+departementCode);
		} else {
			return resultat;
		}
    }
	
	
	
}
