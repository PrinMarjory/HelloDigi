package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.dao.DepartementDAO;
import fr.diginamic.HelloDigi.model.Departement;
import jakarta.persistence.NoResultException;

@Service
public class DepartementService {
	
	@Autowired
	DepartementDAO departementDAO;

	public List<Departement> extractDepartements() {
		return departementDAO.findAll();
	}
	
	public Departement extractDepartement(Long id) {
		Departement departementFromDB = null;
		try {
			departementFromDB = departementDAO.find(id);
		}
		catch (NoResultException e) {
		}
		return departementFromDB;
	}
	
	public Departement extractDepartement(String code) {
		Departement departementFromDB = null;
		try {
			departementFromDB = departementDAO.findByCode(code);
		}
		catch (NoResultException e) {
		}
		return departementFromDB;
	}
	
	public boolean insertDepartement(Departement departement) {
		try {
			departementDAO.findByCode(departement.getCode());
			return false;
		}
		catch(NoResultException nre){
			departementDAO.create(departement);
			return true;
		}
	}

	public boolean modifierDepartement(Departement departement) {
		try {
			Departement departementFromDB = departementDAO.find(departement.getId());
			departementFromDB.setNom(departement.getNom());
			departementFromDB.setCode(departement.getCode());
			departementDAO.update(departementFromDB);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}

	public boolean supprimerDepartement(Long idDepartement) {
		try {
			departementDAO.find(idDepartement);
			departementDAO.deleteById(idDepartement);
			return true;
		}
		catch(NoResultException nre){
			return false;
		}
	}
	
}
