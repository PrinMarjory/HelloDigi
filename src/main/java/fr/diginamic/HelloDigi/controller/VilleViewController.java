package fr.diginamic.HelloDigi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.HelloDigi.service.VilleService;

@Controller
public class VilleViewController {
	
	@Autowired
	private VilleService villeService;
	
	@GetMapping("/villes")
	public ModelAndView getVilles() {
		Map<String, Object> model = new HashMap<>();
		model.put("villes", villeService.extractVilles());
		return new ModelAndView("ville/villes", model);
	}
}
