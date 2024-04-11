package fr.diginamic.HelloDigi.controleurs;

import fr.diginamic.HelloDigi.dto.VilleDTO;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.service.VilleService;
import fr.diginamic.HelloDigi.mapper.VilleMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
		
	@Autowired
	private VilleService villeService;
	
	@Autowired
	private VilleMapper villeMapper;
	
	@GetMapping
	public List<VilleDTO> getVilles() {
		return villeService.extractVilles().stream().map(ville -> villeMapper.toDto(ville)).toList();
	}
	
	@GetMapping("/{id}")
	public VilleDTO getVilleParId(@PathVariable Long id) {;
		return villeMapper.toDto(villeService.extractVille(id));
	}
	
	@GetMapping("nom/{nom}")
	public VilleDTO getVilleParNom(@PathVariable String nom) {
		return villeMapper.toDto(villeService.extractVille(nom));
	}
	
	@PostMapping
	public ResponseEntity<String>insertVille(@RequestBody VilleDTO newVille){
		boolean resultat = villeService.insertVille(villeMapper.toBean(newVille));
		if (resultat) {
			return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<String>updateVille(@RequestBody VilleDTO newVille){
		boolean resultat = villeService.modifierVille(villeMapper.toBean(newVille));
		if (resultat) {
			return new ResponseEntity<String>("Ville modifiée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteVille(@PathVariable Long id){
		boolean resultat = villeService.supprimerVille(id);
		if (resultat) {
			return new ResponseEntity<String>("Ville supprimée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/nom/startingwith/{prefix}")
	public List<VilleDTO> findByNomStartingWith(@PathVariable String prefix) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNomStartingWith(prefix)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
	}
	
	@GetMapping("/population/sup/{minPopulation}")
	public List<VilleDTO> findByNbHabitantsGreaterThan(@PathVariable int minPopulation) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNbHabitantsGreaterThan(minPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
	}
	
	@GetMapping("/population/entre/{minPopulation}/{maxPopulation}")
    public List<VilleDTO> findByNbHabitantsBetween(@PathVariable int minPopulation, @PathVariable int maxPopulation) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNbHabitantsBetween(minPopulation, maxPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }

    @GetMapping("/departement/{departementCode}/population/sup/{minPopulation}")
    public List<VilleDTO> findByDepartementCodeAndNbHabitantsGreaterThan(@PathVariable String departementCode, @PathVariable int minPopulation) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeAndNbHabitantsGreaterThan(departementCode, minPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }

    @GetMapping("/departement/{departementCode}/population/entre/{minPopulation}/{maxPopulation}")
    public List<VilleDTO> findByDepartementCodeAndNbHabitantsBetween(@PathVariable String departementCode, @PathVariable int minPopulation, @PathVariable int maxPopulation) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeAndNbHabitantsBetween(departementCode, minPopulation, maxPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }
    
    @GetMapping("/departement/{departementCode}/top/{size}")
    public List<VilleDTO> findByDepartementCodeOrderByNbHabitantsDesc(@PathVariable String departementCode, @PathVariable int size) {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeOrderByNbHabitantsDesc(departementCode, size)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }
	
}
