package fr.diginamic.HelloDigi.controleurs;

import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.service.DepartementService;
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
@RequestMapping("/departement")
public class DepartementControleur {
		
	@Autowired
	private DepartementService departementService;
	
	@GetMapping
	public List<Departement> getDepartements() {
		return departementService.extractDepartements();
	}
	
	@GetMapping("/{id}")
	public Departement getDepartementParId(@PathVariable Long id) {
		return departementService.extractDepartement(id);
	}
	
	@GetMapping("/code/{code}")
	public Departement getDepartementParCode(@PathVariable String code) {
		return departementService.extractDepartement(code);
	}
	
	@PostMapping
	public ResponseEntity<String>insertDepartement(@RequestBody Departement newDepartement){
		boolean resultat = departementService.insertDepartement(newDepartement);
		if (resultat) {
			return new ResponseEntity<String>("Département inséré avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La département existe déjà",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<String>updateDepartement(@RequestBody Departement newDepartement){
		boolean resultat = departementService.modifierDepartement(newDepartement);
		if (resultat) {
			return new ResponseEntity<String>("Département modifié avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La département n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteDepartement(@PathVariable Long id){
		boolean resultat = departementService.supprimerDepartement(id);
		if (resultat) {
			return new ResponseEntity<String>("Département supprimé avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La département n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
}