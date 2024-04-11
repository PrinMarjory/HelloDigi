package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.repository.DepartementRepository;
import jakarta.persistence.NoResultException;

@Service
public class DepartementService {
	
	@Autowired
	DepartementRepository departementRepository;

	public List<Departement> extractDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}
	
	public Departement extractDepartement(Long id) {
		Departement departementFromDB = null;
		try {
			departementFromDB = departementRepository.findById(id).get();
		}
		catch (NoResultException e) {
		}
		return departementFromDB;
	}
	
	public Departement extractDepartement(String code) {
		Departement departementFromDB = null;
		try {
			departementFromDB = departementRepository.findByCode(code);
		}
		catch (NoResultException e) {
		}
		return departementFromDB;
	}
	
	public boolean insertDepartement(Departement departement) {
		try {
			departementRepository.findByCode(departement.getCode());
			return false;
		}
		catch(NoResultException nre){
			departementRepository.save(departement);
			return true;
		}
	}

	public boolean modifierDepartement(Departement departement) {
		try {
			Departement departementFromDB = departementRepository.findById(departement.getId()).get();
			departementFromDB.setNom(departement.getNom());
			departementFromDB.setCode(departement.getCode());
			departementRepository.save(departementFromDB);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}

	public boolean supprimerDepartement(Long idDepartement) {
		try {
			departementRepository.findById(idDepartement);
			departementRepository.deleteById(idDepartement);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}
	
}
