package fr.diginamic.HelloDigi.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class DepartementDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void create(Departement depart) {
		em.persist(depart);
	}
	
	public List<Departement> findAll() {
		TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d", Departement.class);
		return query.getResultList();
	}
	
	public Departement find(Long id) {
		return em.find(Departement.class, id);
	}
	
	public Departement findByCode(String code) {
		TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code=:param", Departement.class);
		query.setParameter("param", code);
		return query.getSingleResult();
	}
	
	@Transactional
	public Departement update(Departement depart) {
		return em.merge(depart);
	}
	
	@Transactional
	public void delete(Departement depart) {
		em.remove(depart);
	}
	
	@Transactional
	public void deleteById(Long id) {
		Departement depart = em.find(Departement.class, id);
		em.remove(depart);
	}
}
