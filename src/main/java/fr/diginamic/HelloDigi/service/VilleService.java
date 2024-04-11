package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.repository.DepartementRepository;
import fr.diginamic.HelloDigi.repository.VilleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;

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
		Ville villeFromDB = null;
		try {
			villeFromDB = villeRepository.findById(id).get();
		}
		catch (NoResultException e) {
		}
		return villeFromDB;
	}
	
	public Ville extractVille(String nom) {
		Ville villeFromDB = null;
		try {
			villeFromDB = villeRepository.findByNom(nom);
		}
		catch (NoResultException e) {
		}
		return villeFromDB;
	}
	
	public boolean insertVille(Ville ville) {
		try {
			villeRepository.findByNom(ville.getNom());
			return false;
		}
		catch(NoResultException nre){
			villeRepository.save(ville);
			return true;
		}
	}

	public boolean modifierVille(Ville ville) {
		try {
			Ville villeFromDB = villeRepository.findById(ville.getId()).get();
			villeFromDB.setNom(ville.getNom());
			villeFromDB.setNbHabitants(ville.getNbHabitants());
			villeRepository.save(villeFromDB);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}

	public boolean supprimerVille(Long idVille) {
		try {
			villeRepository.findById(idVille);
			villeRepository.deleteById(idVille);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}
	
	public Iterable<Ville> findByNomStartingWith(String prefix) {
		return villeRepository.findByNomStartingWith(prefix);
	}
    
    public Iterable<Ville> findByNbHabitantsGreaterThan(int minPopulation) {
    	return villeRepository.findByNbHabitantsGreaterThan(minPopulation);
    }
    
    public Iterable<Ville> findByNbHabitantsBetween(int minPopulation, int maxPopulation) {
    	return villeRepository.findByNbHabitantsBetween(minPopulation, maxPopulation);
    }
    
    public Iterable<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String departementCode, int minPopulation) {
    	return villeRepository.findByDepartementCodeAndNbHabitantsGreaterThan(departementCode, minPopulation);
    }
    
    public Iterable<Ville> findByDepartementCodeAndNbHabitantsBetween(String departementCode, int minPopulation, int maxPopulation) {
    	return villeRepository.findByDepartementCodeAndNbHabitantsBetween(departementCode, minPopulation, maxPopulation);
    }
    
    public Iterable<Ville> findByDepartementCodeOrderByNbHabitantsDesc(String departementCode, Integer size) {
    	return villeRepository.findByDepartementCodeOrderByNbHabitantsDesc(departementCode, Pageable.ofSize(size)).getContent();
    }
	
	
	
}
