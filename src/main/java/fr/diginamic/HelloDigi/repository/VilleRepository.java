package fr.diginamic.HelloDigi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import fr.diginamic.HelloDigi.model.Ville;

public interface VilleRepository extends CrudRepository<Ville, Long> {
	
	public Ville findByNomAndDepartementCode (String nom, String departementCode);
	
	public Ville findByNom(String nom);
	
	public List<Ville> findByNomStartingWith(String prefix);
    
    public List<Ville> findByNbHabitantsGreaterThan(int minPopulation);
    
    public List<Ville> findByNbHabitantsBetween(int minPopulation, int maxPopulation);
    
    public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String departementCode, int minPopulation);
    
    public List<Ville> findByDepartementCodeAndNbHabitantsBetween(String departementCode, int minPopulation, int maxPopulation);
    
    public Page<Ville> findByDepartementCodeOrderByNbHabitantsDesc(String departementCode, Pageable pageable);
}
