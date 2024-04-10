package fr.diginamic.HelloDigi.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class VilleDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void create(Ville ville) {
		em.persist(ville);
	}
	
	public List<Ville> findAll() {
		TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v", Ville.class);
		return query.getResultList();
	}
	
	public Ville find(Long id) {
		return em.find(Ville.class, id);
	}
	
	public Ville findByName(String nom) {
		TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.nom=:param", Ville.class);
		query.setParameter("param", nom);
		return query.getSingleResult();
	}
	
	@Transactional
	public Ville update(Ville ville) {
		return em.merge(ville);
	}
	
	@Transactional
	public void delete(Ville ville) {
		em.remove(ville);
	}
	
	@Transactional
	public void deleteById(Long id) {
		Ville ville = em.find(Ville.class, id);
		em.remove(ville);
	}
}
