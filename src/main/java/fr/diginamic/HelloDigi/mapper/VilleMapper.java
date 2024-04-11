package fr.diginamic.HelloDigi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.diginamic.HelloDigi.dto.VilleDTO;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.service.DepartementService;
import fr.diginamic.HelloDigi.service.VilleService;

@Component
public class VilleMapper {
	
	@Autowired
	private DepartementService departementService;
	
	@Autowired
	private VilleService villeService;
	
	public VilleDTO toDto(Ville ville) {
		VilleDTO dto = new VilleDTO();
		dto.setId(ville.getId());
		dto.setNom(ville.getNom());
		dto.setNbHabitants(ville.getNbHabitants());
		dto.setCodeDepartement(ville.getDepartement().getCode());
		dto.setNomDepartement(ville.getDepartement().getNom());
		return dto;
	}
	
	public Ville toBean(VilleDTO villeDTO) {
		Ville ville = new Ville();
		ville.setNom(villeDTO.getNom());
		ville.setNbHabitants(villeDTO.getNbHabitants());
		Departement dep = new Departement();
		dep.setCode(villeDTO.getCodeDepartement());
		dep.setNom(villeDTO.getNomDepartement());
		ville.setDepartement(dep);
		Ville v = villeService.extractVille(villeDTO.getNom());
		if (v != null) {
			ville.setId(v.getId());
		}
		Departement d = departementService.extractDepartement(villeDTO.getCodeDepartement());
		if (d != null) {
			dep.setId(d.getId());
		}
		return ville;
	}
	
}

