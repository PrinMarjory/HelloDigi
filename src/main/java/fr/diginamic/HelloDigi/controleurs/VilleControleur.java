package fr.diginamic.HelloDigi.controleurs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import fr.diginamic.HelloFigi.model.Ville;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
	
	List<Ville> villes = new ArrayList<>(Arrays.asList(new Ville("Paris",2145906), new Ville("Marseille",873000), new Ville("Lyon",537000), new Ville("Toulouse",503481), new Ville("Nice",342669), new Ville("Nantes",324580)));
	
	@GetMapping
	public List<Ville> getVilles() {
		return villes;
	}
	
	@GetMapping("/{id}")
	public Ville getVilleParId(@PathVariable Long id) {
		Ville ville = findVilleParId(id);
		return ville;
	}
	
	@GetMapping("nom/{nom}")
	public Ville getVilleParNom(@PathVariable String nom) {
		Ville ville = findVilleParNom(nom);
		return ville;
	}
	
	@PostMapping
	public ResponseEntity<String>insertVille(@RequestBody Ville newVille){
		Ville resultat = findVilleParNom(newVille.getNom());
		if (resultat != null) {
			return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
		}
		villes.add(newVille);
		return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String>updateVille(@RequestBody Ville newVille){
		Ville resultat = findVilleParId(newVille.getId());
		if (resultat == null) {
			return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
		}
		resultat.setNom(newVille.getNom());
		resultat.setNbHabitants(newVille.getNbHabitants());
		return new ResponseEntity<String>("Ville modifiée avec succès",HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteVille(@PathVariable Long id){
		Ville resultat = findVilleParId(id);
		if (resultat == null) {
			return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
		}
		villes.remove(resultat);
		return new ResponseEntity<String>("Ville supprimée avec succès",HttpStatus.OK);
	}
	
	private Ville findVilleParNom(String nom) {
		Ville resultat = this.villes.stream().filter(element -> nom.equals(element.getNom())).findAny().orElse(null);
		return resultat;
	}

	private Ville findVilleParId(Long id) {
		Ville resultat = this.villes.stream().filter(element -> id.equals(element.getId())).findAny().orElse(null);
		return resultat;
	}
	
}
