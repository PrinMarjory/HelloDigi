package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.dao.VilleDAO;
import fr.diginamic.HelloDigi.model.Ville;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;

@Service
public class VilleService {
	
	@Autowired
	VilleDAO villeDAO;
	
	@PostConstruct
	public void init() {
		//Création de la table Ville à l'initialisation
		villeDAO.create(new Ville("Paris",2133111));
		villeDAO.create(new Ville("Marseille",156224));
		villeDAO.create(new Ville("Lyon",456952));
		villeDAO.create(new Ville("Nantes",255692));
	}	

	public List<Ville> extractVilles() {
		return villeDAO.findAll();
	}
	
	public Ville extractVille(Long id) {
		Ville villeFromDB = null;
		try {
			villeFromDB = villeDAO.find(id);
		}
		catch (NoResultException e) {
		}
		return villeFromDB;
	}
	
	public Ville extractVille(String nom) {
		Ville villeFromDB = null;
		try {
			villeFromDB = villeDAO.findByName(nom);
		}
		catch (NoResultException e) {
		}
		return villeFromDB;
	}
	
	public boolean insertVille(Ville ville) {
		try {
			villeDAO.findByName(ville.getNom());
			return false;
		}
		catch(NoResultException nre){
			villeDAO.create(ville);
			return true;
		}
	}

	public boolean modifierVille(Ville ville) {
		try {
			Ville villeFromDB = villeDAO.find(ville.getId());
			villeFromDB.setNom(ville.getNom());
			villeFromDB.setNbHabitants(ville.getNbHabitants());
			villeDAO.update(villeFromDB);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}

	public boolean supprimerVille(Long idVille) {
		try {
			villeDAO.find(idVille);
			villeDAO.deleteById(idVille);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}
	
}
