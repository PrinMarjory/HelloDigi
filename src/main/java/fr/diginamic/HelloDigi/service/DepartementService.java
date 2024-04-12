package fr.diginamic.HelloDigi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.repository.DepartementRepository;

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
	
	public boolean insertDepartement(Departement departement) throws FunctionalException {
		Departement d = departementRepository.findByCode(departement.getCode());
		if (d != null) {
			return false;
		} else {
			if(departement.getCode().length()<2 && departement.getCode().length()>3 ) {
				throw new FunctionalException("Le code du département doit contenir deux ou trois caractères");
			}	
			if(departement.getNom().length()<3) {
				throw new FunctionalException("Le nom du département doit contenir au moins trois caractères");
			}
			departementRepository.save(departement);
			return true;
		}
	}

	public boolean modifierDepartement(Departement departement) throws FunctionalException {
		Departement departementFromDB = departementRepository.findById(departement.getId()).get();
		if (departementFromDB != null) {
			if(departement.getCode().length()<2 && departement.getCode().length()>3 ) {
				throw new FunctionalException("Le code du département doit contenir deux ou trois caractères");
			}	
			if(departement.getNom().length()<3) {
				throw new FunctionalException("Le nom du département doit contenir au moins trois caractères");
			}
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
