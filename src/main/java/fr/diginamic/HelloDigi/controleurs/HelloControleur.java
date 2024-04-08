package fr.diginamic.HelloDigi.controleurs;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloControleur {
	
	@GetMapping
	public String direHello() {
		return "Hello";
	}
}
