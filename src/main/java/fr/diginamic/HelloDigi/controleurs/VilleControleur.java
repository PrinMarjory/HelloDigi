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
		List<Ville> resultat = villeService.insertVille(newVille);
		if (resultat == null) {
			return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String>updateVille(@RequestBody Ville newVille){
		List<Ville> resultat = villeService.modifierVille(newVille);
		if (resultat == null) {
			return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Ville modifiée avec succès",HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteVille(@PathVariable Long id){
		List<Ville> resultat = villeService.supprimerVille(id);
		if (resultat == null) {
			return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Ville supprimée avec succès",HttpStatus.OK);
	}
	
}
