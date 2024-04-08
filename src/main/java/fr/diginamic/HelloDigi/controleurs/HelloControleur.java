package fr.diginamic.HelloDigi.controleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.HelloDigi.service.HelloService;

@RestController
@RequestMapping("/hello")
public class HelloControleur {
	
	@Autowired
	private HelloService helloService;
	
	@GetMapping
	public String direHello() {
		return helloService.salutations();		
	}
}
