package fr.diginamic.HelloDigi.service;

import java.util.List;
import org.springframework.stereotype.Service;

import fr.diginamic.HelloDigi.model.Ville;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class VilleService {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Transactional
	public List<Ville> extractVilles() {
		try {
			TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v", Ville.class);
			return query.getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public Ville extractVille(String nom) {
		try {
			TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.nom=:param", Ville.class);
			query.setParameter("param", nom);
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}

	}

	@Transactional
	public Ville extractVille(Long id) {
		try {
			TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.id=:param", Ville.class);
			query.setParameter("param", id);
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public List<Ville> insertVille(Ville ville) {
		Ville villeFromDB = extractVille(ville.getNom());
		if (villeFromDB != null) {
			return null;
		}
		em.persist(ville);
		return extractVilles();
	}
	
	@Transactional
	public List<Ville> modifierVille(Ville ville) {
		Ville villeFromDB = em.find(Ville.class, ville.getId());
		if (villeFromDB == null) {
			return null;
		}
		villeFromDB.setNom(ville.getNom());
		villeFromDB.setNbHabitants(ville.getNbHabitants());
		return extractVilles();
	}
	
	@Transactional
	public List<Ville> supprimerVille(Long idVille) {
		Ville villeFromDB = em.find(Ville.class, idVille);
		if (villeFromDB == null) {
			return null;
		}
		em.remove(villeFromDB);
		return extractVilles();
	}
	
}
