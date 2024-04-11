package fr.diginamic.HelloDigi.mapper;

import org.springframework.stereotype.Component;

import fr.diginamic.HelloDigi.dto.DepartementDTO;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;

@Component
public class DepartementMapper {
	
	public DepartementDTO toDto(Departement depart) {
		DepartementDTO dto = new DepartementDTO();
		dto.setId(depart.getId());
		dto.setCode(depart.getCode());
		dto.setNom(depart.getNom());
		int nbHabitants = 0;
		for (Ville v : depart.getVilles()) {
			nbHabitants += v.getNbHabitants();
		}
		dto.setNbHabitants(nbHabitants);
		return dto;
	}
	
}
