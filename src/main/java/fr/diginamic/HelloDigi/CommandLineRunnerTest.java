package fr.diginamic.HelloDigi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineRunnerTest implements CommandLineRunner{

	public static void main(String[] args) {		
		SpringApplication application = new SpringApplication(HelloDigiApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inside Run !");
	}
}
