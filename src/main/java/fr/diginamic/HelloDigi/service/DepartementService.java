package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.repository.DepartementRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
public class DepartementService {
	
	@Autowired
	DepartementRepository departementRepository;

	public List<Departement> extractDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}
	
	public Departement extractDepartement(Long id) {
		return departementRepository.findById(id).get();
	}
	
	public Departement extractDepartement(String code) {
		return departementRepository.findByCode(code);
	}
	
	public boolean insertDepartement(Departement departement) {
		Departement d = departementRepository.findByCode(departement.getCode());
		if (d != null) {
			return false;
		} else {
			departementRepository.save(departement);
			return true;
		}
	}

	public boolean modifierDepartement(Departement departement) {
		Departement departementFromDB = departementRepository.findById(departement.getId()).get();
		if (departementFromDB != null) {
			departementFromDB.setNom(departement.getNom());
			departementFromDB.setCode(departement.getCode());
			departementRepository.save(departementFromDB);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean supprimerDepartement(Long idDepartement) {
		Departement departementFromDB = departementRepository.findById(idDepartement).get();
		if (departementFromDB != null){
			departementRepository.deleteById(idDepartement);
			return true;
		}
		else {
			return false;
		}
	}
	
}
