package fr.diginamic.HelloDigi.controleurs;

import fr.diginamic.HelloDigi.dto.VilleDTO;
import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.service.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import fr.diginamic.HelloDigi.mapper.VilleMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
	
	@Value("${hellodigi.export}")
	private String file;
		
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
	
	@GetMapping("nom/{nom}/export/pdf")
	public void exportPDF(@PathVariable String nom, HttpServletResponse response) throws IOException,DocumentException {
		
		VilleDTO ville = villeMapper.toDto(villeService.extractVille(nom));
		Document docPdf = new Document(PageSize.A4);
		
		response.setHeader("Content-Disposition","attachment; filename=\""+nom+"-"+file+".pdf\"");
		
		PdfWriter.getInstance(docPdf, response.getOutputStream());
		
		docPdf.open();
		docPdf.addAuthor("Marjory PRIN");
		docPdf.addTitle("Fiche ville - "+ville.getNom());
		docPdf.newPage();
		BaseFont basefont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Phrase p1 = new Phrase("Nom de la ville : "+ville.getNom()+"\n", new Font(basefont, 32.0f, 1, new BaseColor(0, 51, 88)));
		Phrase p2 = new Phrase("Nombre d'habitants : "+ville.getNbHabitants()+" hab.\n", new Font(basefont, 32.0f, 1, new BaseColor(0, 51, 88)));
		Phrase p3 = new Phrase("Département : "+ville.getNomDepartement()+" ("+ville.getCodeDepartement()+")\n", new Font(basefont, 32.0f, 1, new BaseColor(0, 51, 88)));
		docPdf.add(p1);
		docPdf.add(p2);
		docPdf.add(p3);
		docPdf.close();
		
		response.flushBuffer();
	}
	
	@GetMapping("/export/csv")
	public void exportCSV(HttpServletResponse response) throws IOException {
		
		List<VilleDTO> villes = villeService.extractVilles().stream().map(ville -> villeMapper.toDto(ville)).toList();
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition","attachment; filename=\"Villes-"+file+".csv\"");
		
		PrintWriter writer = response.getWriter();
		writer.println("Nom de la ville,Nombre d'habitants,Code département,Nom du département");
		
        for (VilleDTO ville : villes) {
            writer.println(ville.getNom() + "," + ville.getNbHabitants() + "," + ville.getCodeDepartement() + "," + ville.getNomDepartement());
        }
	}
	
	@PostMapping
	public ResponseEntity<String>insertVille(@RequestBody VilleDTO newVille) throws FunctionalException {
		boolean resultat = villeService.insertVille(villeMapper.toBean(newVille));
		if (resultat) {
			return new ResponseEntity<String>("Ville insérée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville existe déjà",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<String>updateVille(@RequestBody VilleDTO newVille) throws FunctionalException {
		boolean resultat = villeService.modifierVille(villeMapper.toBean(newVille));
		if (resultat) {
			return new ResponseEntity<String>("Ville modifiée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteVille(@PathVariable Long id) {
		boolean resultat = villeService.supprimerVille(id);
		if (resultat) {
			return new ResponseEntity<String>("Ville supprimée avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La ville n'existe pas",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/nom/startingwith/{prefix}")
	public List<VilleDTO> findByNomStartingWith(@PathVariable String prefix) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNomStartingWith(prefix)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
	}
	
	@GetMapping("/population/sup/{minPopulation}")
	public List<VilleDTO> findByNbHabitantsGreaterThan(@PathVariable int minPopulation) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNbHabitantsGreaterThan(minPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
	}
	
	@GetMapping("/population/entre/{minPopulation}/{maxPopulation}")
    public List<VilleDTO> findByNbHabitantsBetween(@PathVariable int minPopulation, @PathVariable int maxPopulation) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByNbHabitantsBetween(minPopulation, maxPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }

    @GetMapping("/departement/{departementCode}/population/sup/{minPopulation}")
    public List<VilleDTO> findByDepartementCodeAndNbHabitantsGreaterThan(@PathVariable String departementCode, @PathVariable int minPopulation) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeAndNbHabitantsGreaterThan(departementCode, minPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }

    @GetMapping("/departement/{departementCode}/population/entre/{minPopulation}/{maxPopulation}")
    public List<VilleDTO> findByDepartementCodeAndNbHabitantsBetween(@PathVariable String departementCode, @PathVariable int minPopulation, @PathVariable int maxPopulation) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeAndNbHabitantsBetween(departementCode, minPopulation, maxPopulation)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }
    
    @GetMapping("/departement/{departementCode}/top/{size}")
    public List<VilleDTO> findByDepartementCodeOrderByNbHabitantsDesc(@PathVariable String departementCode, @PathVariable int size) throws FunctionalException {
		List<VilleDTO> resultat = new ArrayList<>();
		for (Ville v : villeService.findByDepartementCodeOrderByNbHabitantsDesc(departementCode, size)) {
			resultat.add(villeMapper.toDto(v));
		}
		return resultat;
    }
	
}
