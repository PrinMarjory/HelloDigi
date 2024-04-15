package fr.diginamic.HelloDigi.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.diginamic.HelloDigi.mapper.DepartementMapper;
import fr.diginamic.HelloDigi.mapper.VilleMapper;
import fr.diginamic.HelloDigi.model.Departement;
import fr.diginamic.HelloDigi.model.Ville;
import fr.diginamic.HelloDigi.repository.DepartementRepository;
import fr.diginamic.HelloDigi.repository.VilleRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class VilleControleurTest {
	
	@Autowired
	private VilleMapper villeMapper;
	
	@Autowired
	private DepartementMapper departementMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private VilleRepository villeRepository;
	
	@MockBean
	private DepartementRepository departementRepository;

	@Test
	public void getVilles() throws Exception {
		Ville v1 = new Ville("Brains", 10000, new Departement("Loire-Atlantique", "44"));
		Ville v2 = new Ville("Brest", 140000, new Departement("Finistère", "29"));
	
		when(villeRepository.findAll()).thenReturn(List.of(v1,v2));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/villes")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Brains")))
			.andExpect(jsonPath("$[0].nom", is("Brains")))
			.andExpect(jsonPath("$[0].departement.code", is("44")));
	}
}
