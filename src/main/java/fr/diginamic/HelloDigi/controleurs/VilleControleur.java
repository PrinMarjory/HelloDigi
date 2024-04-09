package fr.diginamic.HelloDigi.controleurs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.bind.DataObjectPropertyName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<String>insertVille(@RequestBody Ville newVille){
		Ville resultat = findVilleParNom(newVille.getNom());
		if (resultat != null) {
			return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
		}
		villes.add(newVille);
		return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
	}
	
	private Ville findVilleParNom(String nom) {
		Ville resultat = this.villes.stream().filter(element -> nom.equals(element.getNom())).findAny().orElse(null);
		return resultat;
	}
	
}
