package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private OfertaService ofertasService;

	@PostConstruct
	public void init() {
		User user1 = new User("pedro@email.es", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole("ROLE_USER");

		User user2 = new User("lucas@email.es", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setRole("ROLE_USER");

		User user3 = new User("maria@email.es", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole("ROLE_USER");

		User user4 = new User("almonte@email.es", "Marta", "Almonte");
		user4.setPassword("123456");
		user4.setRole("ROLE_USER");

		User user5 = new User("valdes@email.es", "Pelayo", "Valdes");
		user5.setPassword("123456");
		user5.setRole("ROLE_USER");

		User user6 = new User("admin@email.es", "Edward", "Núñez");
		user6.setPassword("123456");
		user6.setRole("ROLE_ADMIN");

		Oferta oferta1 = new Oferta(user1, "Camiseta azul", "Talla L", 100);
		Oferta oferta2 = new Oferta(user1, "Sudadera azul", "Talla M", 5);
		Oferta oferta3 = new Oferta(user1, "Pantalon azul", "Talla XL", 3);
		Oferta oferta4 = new Oferta(user2, "Zapatos nike", "Talla L", 10);
		Oferta oferta5 = new Oferta(user2, "Sudadera nike", "Talla M", 4);
		Oferta oferta6 = new Oferta(user2, "Pantalon nike", "Talla XL", 12);
		Oferta oferta7 = new Oferta(user3, "Zapatos adidas", "Talla L", 10);
		Oferta oferta8 = new Oferta(user3, "Sudadera adidas", "Talla M", 2);
		Oferta oferta9 = new Oferta(user3, "Pantalon adidas", "Talla XL", 5);
		Oferta oferta10 = new Oferta(user4, "Calcetines", "Seminuevos", 6);
		Oferta oferta11 = new Oferta(user4, "Chandal completo", "Talla M", 9);
		Oferta oferta12 = new Oferta(user4, "Chubasquero", "Talla XL", 11);
		Oferta oferta13 = new Oferta(user5, "Zapatos", "Seminuevos", 2);
		Oferta oferta14 = new Oferta(user5, "Sudadera", "Talla M", 6);
		Oferta oferta15 = new Oferta(user5, "Pantalon", "Talla XL", 1);


		
		List<Oferta> user1Ofertas = null;
		
			user1Ofertas = new ArrayList<Oferta>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					
					add(oferta1);
					add(oferta2);
					add(oferta3);
				}
			};
		
		user1.setOfertas(new HashSet<>(user1Ofertas));

		ArrayList<Oferta> user2Ofertas = null;
			user2Ofertas = new ArrayList<Oferta>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					add(oferta4);
					add(oferta5);
					add(oferta6);
				}
			};
		user2.setOfertas(new HashSet<>(user2Ofertas));

		List<Oferta> user3Ofertas = null;
		user3Ofertas = new ArrayList<Oferta>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					
					add(oferta7);
					add(oferta8);
					add(oferta9);
				}
			};
		
		user3.setOfertas(new HashSet<>(user3Ofertas));

		List<Oferta> user4Ofertas = null;
		
			user4Ofertas = new ArrayList<Oferta>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					
					add(oferta10);
					add(oferta11);
					add(oferta12);
				}
			};
		
		user4.setOfertas(new HashSet<>(user4Ofertas));

		List<Oferta> user5Ofertas = null;
		
			user5Ofertas = new ArrayList<Oferta>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					
					add(oferta13);
					add(oferta14);
					add(oferta15);
				}
			};
		
		user5.setOfertas(new HashSet<>(user5Ofertas));

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);

		// Asignar compras hechas por usuarios
		ofertasService.comprarOferta(user1, user2Ofertas.get(1));
		ofertasService.comprarOferta(user1, user3Ofertas.get(2));

		ofertasService.comprarOferta(user2, user3Ofertas.get(1));
		ofertasService.comprarOferta(user2, user4Ofertas.get(2));

		ofertasService.comprarOferta(user3, user4Ofertas.get(1));
		ofertasService.comprarOferta(user3, user5Ofertas.get(2));

		ofertasService.comprarOferta(user4, user5Ofertas.get(1));
		ofertasService.comprarOferta(user4, user1Ofertas.get(2));

		ofertasService.comprarOferta(user5, user1Ofertas.get(1));
		ofertasService.comprarOferta(user5, user2Ofertas.get(2));


	}

}