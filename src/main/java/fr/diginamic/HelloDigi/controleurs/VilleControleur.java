package fr.diginamic.HelloDigi.controleurs;

import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.service.VilleService;
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
	
	@GetMapping
	public List<Ville> getVilles() {
		return villeService.extractVilles();
	}
	
	@GetMapping("/{id}")
	public Ville getVilleParId(@PathVariable Long id) {
		return villeService.extractVille(id);
	}
	
	@GetMapping("nom/{nom}")
	public Ville getVilleParNom(@PathVariable String nom) {
		return villeService.extractVille(nom);
	}
	
	@PostMapping
	public ResponseEntity<String>insertVille(@RequestBody Ville newVille){
		boolean resultat = villeService.insertVille(newVille);
		if (resultat) {
			return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<String>updateVille(@RequestBody Ville newVille){
		boolean resultat = villeService.modifierVille(newVille);
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
	public Iterable<Ville> findByNomStartingWith(@PathVariable String prefix) {
		return villeService.findByNomStartingWith(prefix);
	}
	
	@GetMapping("/population/sup/{minPopulation}")
	public Iterable<Ville> findByNbHabitantsGreaterThan(@PathVariable int minPopulation) {
		return villeService.findByNbHabitantsGreaterThan(minPopulation);
	}
	
	@GetMapping("/population/entre/{minPopulation}/{maxPopulation}")
    public Iterable<Ville> findByNbHabitantsBetween(@PathVariable int minPopulation, @PathVariable int maxPopulation) {
        return villeService.findByNbHabitantsBetween(minPopulation, maxPopulation);
    }

    @GetMapping("/departement/{departementCode}/population/sup/{minPopulation}")
    public Iterable<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(@PathVariable String departementCode, @PathVariable int minPopulation) {
        return villeService.findByDepartementCodeAndNbHabitantsGreaterThan(departementCode, minPopulation);
    }

    @GetMapping("/departement/{departementCode}/population/entre/{minPopulation}/{maxPopulation}")
    public Iterable<Ville> findByDepartementCodeAndNbHabitantsBetween(@PathVariable String departementCode, @PathVariable int minPopulation, @PathVariable int maxPopulation) {
        return villeService.findByDepartementCodeAndNbHabitantsBetween(departementCode, minPopulation, maxPopulation);
    }
    
    @GetMapping("/departement/{departementCode}/top/{size}")
    public Iterable<Ville> findByDepartementCodeOrderByNbHabitantsDesc(@PathVariable String departementCode, @PathVariable int size) {
        return villeService.findByDepartementCodeOrderByNbHabitantsDesc(departementCode, size);
    }
	
}
