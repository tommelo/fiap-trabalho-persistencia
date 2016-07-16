package br.com.fiap.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

	public static void main(String [] args) {
		
		EntityManagerFactory emf =
				Persistence.createEntityManagerFactory("jpaPU");
		
		EntityManager em = emf.createEntityManager();
		
		User user = new User();
		user.setName("Aluno Fiap 2");
		
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		
	}
	
}
