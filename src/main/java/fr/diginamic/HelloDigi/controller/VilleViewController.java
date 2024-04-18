package fr.diginamic.HelloDigi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.HelloDigi.service.VilleService;

@Controller
public class VilleViewController {
	
	@Autowired
	private VilleService villeService;
	
	@GetMapping("/villes")
	public ModelAndView getVilles(Authentication authentication) {
		Map<String, Object> model = new HashMap<>();
		String roles = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));
		model.put("villes", villeService.extractVilles());
		model.put("roles", roles);
		return new ModelAndView("ville/villes", model);
	}
}
