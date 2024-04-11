package fr.diginamic.HelloDigi.repository;

import org.springframework.data.repository.CrudRepository;
import fr.diginamic.HelloDigi.model.Departement;


public interface DepartementRepository extends CrudRepository<Departement, Long> {
	
	public Departement findByCode(String code);
	
}
