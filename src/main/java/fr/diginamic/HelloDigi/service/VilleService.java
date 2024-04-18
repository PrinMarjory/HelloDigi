package fr.diginamic.HelloDigi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.repository.VilleRepository;
import jakarta.annotation.PostConstruct;

@Service
public class VilleService {
	
	@Autowired
	VilleRepository villeRepository;
	
	@Autowired
	DepartementService departementService;
	
	/*
	@PostConstruct
	public void init() throws IOException, FunctionalException {
		//Chargement de la base à l'intialisation
		Path path = Paths.get("C:/Users/marjo/Documents/Diginamic/Java/HelloDigi/src/main/resources/cities.csv");
		List<String> lignes = Files.readAllLines(path);
		lignes.remove(0);
		for (String ligne : lignes) {
			String[] morceaux = ligne.split(",");
			Departement depart = departementService.extractDepartement(morceaux[7]);
			if (depart == null) {
				depart = new Departement(morceaux[6],morceaux[7]);
				departementService.insertDepartement(depart);
			}
			Ville ville = new Ville(morceaux[3],0, depart);
			insertVille(ville);
		}	
	}	
	*/

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
		Ville villeFromDB = villeRepository.findByNomAndDepartementCode(ville.getNom(), ville.getDepartement().getCode());
		if (villeFromDB != null) {
			return false;
		} else {
			/*
			if(ville.getNbHabitants()<10) {
				throw new FunctionalException("La ville doit avoir au moins 10 habitants");
			}
			*/
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
			/*
			if(ville.getNbHabitants()<10) {
				throw new FunctionalException("La ville doit avoir au moins 10 habitants");
			}
			*/
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
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
