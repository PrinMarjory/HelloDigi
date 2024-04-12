package fr.diginamic.HelloDigi.controleurs;

import fr.diginamic.HelloDigi.dto.DepartementDTO;
import fr.diginamic.HelloDigi.exception.FunctionalException;
import fr.diginamic.HelloDigi.mapper.DepartementMapper;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.service.DepartementService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
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
@RequestMapping("/departement")
public class DepartementControleur {
	
	@Value("${hellodigi.export}")
	private String file;
		
	@Autowired
	private DepartementService departementService;
	
	@Autowired
	private DepartementMapper departementMapper;
	
	@GetMapping
	public List<DepartementDTO> getDepartements() {
		return departementService.extractDepartements().stream().map(dep -> departementMapper.toDto(dep)).toList();
	}
	
	@GetMapping("/{id}")
	public DepartementDTO getDepartementParId(@PathVariable Long id) {
		return departementMapper.toDto(departementService.extractDepartement(id));
	}
	
	@GetMapping("/code/{code}")
	public DepartementDTO getDepartementParCode(@PathVariable String code) {
		return departementMapper.toDto(departementService.extractDepartement(code));
	}
	
	@GetMapping("/code/{code}/export/pdf")
	public void exportPDF(@PathVariable String code, HttpServletResponse response) throws IOException,DocumentException {
		
		DepartementDTO depart = departementMapper.toDto(departementService.extractDepartement(code));
		Document docPdf = new Document(PageSize.A4);
		
		response.setHeader("Content-Disposition","attachment; filename=\""+code+"-"+file+".pdf\"");
		
		PdfWriter.getInstance(docPdf, response.getOutputStream());
		
		docPdf.open();
		docPdf.addAuthor("Marjory PRIN");
		docPdf.addTitle("Fiche département - "+depart.getNom());
		docPdf.newPage();
		BaseFont basefont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Phrase p1 = new Phrase("Nom du département : "+depart.getNom()+" ("+depart.getCode()+")\n", new Font(basefont, 32.0f, 1, new BaseColor(0, 51, 88)));
		Phrase p2 = new Phrase("Nombre d'habitants : "+depart.getNbHabitants()+" hab.\n", new Font(basefont, 32.0f, 1, new BaseColor(0, 51, 88)));
		docPdf.add(p1);
		docPdf.add(p2);
		docPdf.close();
		
		response.flushBuffer();
	}
	
	@GetMapping("/export/csv")
	public void exportCSV(HttpServletResponse response) throws IOException {
		
		List<DepartementDTO> departements = departementService.extractDepartements().stream().map(dep -> departementMapper.toDto(dep)).toList();
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition","attachment; filename=\"Départements-"+file+".csv\"");
		
		PrintWriter writer = response.getWriter();
		writer.println("Code département,Nom du département,Nombre d'habitants");
		
        for (DepartementDTO depart : departements) {
            writer.println(depart.getCode() + "," + depart.getNom() + "," + depart.getNbHabitants());
        }
	}
	
	@PostMapping
	public ResponseEntity<String>insertDepartement(@RequestBody Departement newDepartement) throws FunctionalException{
		boolean resultat = departementService.insertDepartement(newDepartement);
		if (resultat) {
			return new ResponseEntity<String>("Département inséré avec succès",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La département existe déjà",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<String>updateDepartement(@RequestBody Departement newDepartement) throws FunctionalException {
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
