package fr.diginamic.HelloDigi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.HelloDigi.service.HelloService;

@RestController
@RequestMapping("api/hello")
public class HelloController {
	
	@Autowired
	private HelloService helloService;
	
	@GetMapping
	public String direHello() {
		return helloService.salutations();		
	}
}
