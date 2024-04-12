package fr.diginamic.HelloDigi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;


@SpringBootTest
@ActiveProfiles("test")
public class VilleServiceTest {

		@Autowired
		private VilleService villeService;
		
		@Test
		public void extractVilles() {
			Iterable<Ville> villes = villeService.extractVilles();
			assertTrue(villes.iterator().hasNext());
		}
		
		@Test
		public void extractVilleById(){
			Ville ville = villeService.extractVille(1L);
			assertEquals("Paris", ville.getNom());
		}
		
		@Test
		public void insertVille_Success() throws FunctionalException {
			Ville v  = new Ville("Brest", 123456, new Departement("Finistère", "22"));
			boolean result = villeService.insertVille(v);	
			assertTrue(result);
		}
		
		@Test
		public void insertVille_Failure_Habitants() throws FunctionalException {
			Ville v  = new Ville("Brains", 5, new Departement("loire-Atlantique", "44"));	
			assertThrows(FunctionalException.class, () -> villeService.insertVille(v));
		}
		
}
